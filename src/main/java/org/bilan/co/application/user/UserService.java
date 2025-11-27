package org.bilan.co.application.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.bilan.co.application.teacher.TeacherUtils;
import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.domain.dtos.ResponseDtoBuilder;
import org.bilan.co.domain.dtos.common.PagedResponse;
import org.bilan.co.domain.dtos.user.AuthenticatedUserDto;
import org.bilan.co.domain.dtos.user.EnableUser;
import org.bilan.co.domain.dtos.user.UserInfoDto;
import org.bilan.co.domain.entities.*;
import org.bilan.co.domain.enums.DocumentType;
import org.bilan.co.domain.enums.UserType;
import org.bilan.co.infraestructure.persistance.*;
import org.bilan.co.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.Validator;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService implements IUserService {

    @Autowired
    private StudentsRepository studentsRepository;
    @Autowired
    private TeachersRepository teachersRepository;
    @Autowired
    private MinUserRepository minUserRepository;
    @Autowired
    private StatsRepository statsRepository;
    @Autowired
    private RolesRepository rolesRepository;
    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private CoursesRepository coursesRepository;
    @Autowired
    private SecEduRepository secEduRepository;
    @Autowired
    private TribesRepository tribesRepository;
    @Autowired
    private ClassroomRepository classroomRepository;
    @Autowired
    private CollegesRepository collegesRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private Validator validator;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public AuthenticatedUserDto getUserNameTokenById(AuthenticatedUserDto dataToken) {
        UserInfo user = getUser(dataToken);
        if (user == null)
            return new UserInfoDto();

        return new AuthenticatedUserDto(user.getDocument(),
                dataToken.getUserType(),
                user.getDocumentType(),
                getAuthorities((user.getRole())));
    }


    @Override
    public ResponseDto<UserInfoDto> getUserInfo(String token) {

        AuthenticatedUserDto userAuthenticated = jwtTokenUtil.getInfoFromToken(token);
        UserInfo user = getUser(userAuthenticated);

        if (Objects.isNull(user)) {
            return new ResponseDto<>("The user info couldn't be found", 404, null);
        }

        UserInfoDto result = parseUser(user);

        addExtraProperties(result);

        return new ResponseDtoBuilder<UserInfoDto>().setDescription("Test").setResult(result).setCode(200)
                .createResponseDto();
    }


    private void addStudentExtraProperties(UserInfoDto userInfoDto) {
        Students student = studentsRepository.getById(userInfoDto.getDocument());

        userInfoDto.getMetadata().put("grade", student.getGrade());
        userInfoDto.getMetadata().put("course", student.getCourses().getName());
    }


    private void addExtraProperties(UserInfoDto userInfoDto) {

        switch (userInfoDto.getUserType()) {
            case Student:
                addStudentExtraProperties(userInfoDto);
                break;

            case Teacher:
                List<Classroom> classrooms = classroomRepository.getTeacherClassrooms(userInfoDto.getDocument());
                TeacherUtils.addTeacherExtraProperties(userInfoDto, classrooms);
                addCodDaneSede(userInfoDto);
                break;

            case DirectiveTeacher:
                addCodDaneSede(userInfoDto);
                break;

            case SecEdu:
                addState(userInfoDto);
        }

    }

    private void addCodDaneSede(UserInfoDto userInfoDto) {
        String codDaneSede = teachersRepository.getCodDaneSede(userInfoDto.getDocument());
        userInfoDto.getMetadata().put("codDaneSede", codDaneSede);

        Optional<Colleges> college = collegesRepository.findByCodDaneSede(codDaneSede);
        if (college.isPresent()) {
            userInfoDto.getMetadata().put("cityId", college.get().getStateMunicipality().getId().toString());
            userInfoDto.getMetadata().put("collegeId", college.get().getId().toString());
            userInfoDto.getMetadata().put("state", college.get().getStateMunicipality().getState());
        }
    }

    private void addState(UserInfoDto userInfoDto) {
        Optional<String> state = secEduRepository.getStateFromUser(userInfoDto.getDocument());
        state.ifPresent(s -> userInfoDto.getMetadata().put("state", s));
    }

    private UserInfoDto parseUser(UserInfo user) {
        UserInfoDto result = new UserInfoDto();
        result.setEmail(user.getEmail());
        result.setName(user.getName());
        result.setDocument(user.getDocument());
        result.setLastName(user.getLastName());
        result.setDocumentType(user.getDocumentType());
        result.setGrantedAuthorities(getAuthorities(user.getRole()));
        result.setIsEnabled(user.getIsEnabled());
        result.setUserType(UserType.findByRol(user.getRole().getName()));

        addExtraProperties(result);

        return result;
    }

    public static void updateUserEntityFromDto(UserInfo user, UserInfoDto userInfoDto) {
        user.setIsEnabled(userInfoDto.getIsEnabled());
        user.setName(userInfoDto.getName());
        user.setLastName(userInfoDto.getLastName());
        user.setEmail(user.getEmail());
    }

    @Override
    public ResponseDto<String> updateUserInfo(UserInfoDto userInfoDto, String token) {
        Optional<UserInfo> userInfoToUpdate = userInfoRepository.findById((userInfoDto.getDocument()));

        if (!userInfoToUpdate.isPresent()) {
            return new ResponseDto<>("Failed to update, not found", 404, "Error");
        }
        UserInfo user = userInfoToUpdate.get();
        updateUserEntityFromDto(user, userInfoDto);

        userInfoRepository.save(user);

        return new ResponseDto<>("User updated", 200, "Ok");
    }

    @Override
    public ResponseDto<Boolean> enableUser(EnableUser user) {
        userInfoRepository.updateState(user.getDocument(), user.getEnabled());
        return new ResponseDto<>("User state changed", 200, user.getEnabled());
    }


    @Override
    public ResponseDto<PagedResponse<UserInfoDto>> getUsersAdmin(Integer nPage, String partialDocument, String jwt) {

        AuthenticatedUserDto userDto = jwtTokenUtil.getInfoFromToken(jwt);

        Page<UserInfo> query;

        List<Integer> queryRole = switch (userDto.getUserType()) {
            case DirectiveTeacher -> Collections.singletonList(UserType.Teacher.getId());
            case Teacher -> Collections.singletonList(UserType.Student.getId());
            case SecEdu -> Arrays.asList(UserType.DirectiveTeacher.getId(), UserType.Teacher.getId());
            case Admin -> Arrays.asList(
                    UserType.MinUser.getId(),
                    UserType.Admin.getId(),
                    UserType.SecEdu.getId(),
                    UserType.DirectiveTeacher.getId()
            );
            default -> throw new IllegalArgumentException("Invalid user Type");
        };
        String purgedDocument = partialDocument.trim();
        if (purgedDocument.isEmpty())
            query = userInfoRepository.getUsers(PageRequest.of(nPage, 10), userDto.getDocument(), queryRole);

        else {
            query = userInfoRepository.searchUsersWithDocument(
                    PageRequest.of(nPage, 10), partialDocument, userDto.getDocument(), queryRole);
        }


        PagedResponse<UserInfoDto> userInfoDtoPagedResponse = new PagedResponse<>();
        userInfoDtoPagedResponse.setNPages(query.getTotalPages());

        List<UserInfoDto> users = query.getContent()
                .stream()
                .map(this::parseUser)
                .collect(Collectors.toList());

        userInfoDtoPagedResponse.setData(users);

        return new ResponseDto<>("Users retrieved successfully", 200, userInfoDtoPagedResponse);
    }

    public Teachers processTeacher(String[] user, int nLine, Colleges colleges) throws Exception {
        if (user.length != 5)
            throw new Exception("The line was in bad format");

        String document = user[0];

        if (!teachersRepository.existsById(document))
            throw new IllegalArgumentException("Teacher not found: " + document);


        DocumentType documentType = DocumentType.valueOf(user[1]);
        String grade = user[2];

        validateGrade(grade, nLine);

        String courseString = user[3];

        Courses courses = getCourse(courseString, nLine);

        String tribeName = user[4];

        Classroom classroom = new Classroom();

        Teachers teacher = new Teachers();
        teacher.setDocumentType(documentType);
        teacher.setDocument(document);

        Optional<Tribes> t = tribesRepository.getByName(tribeName);

        if(!t.isPresent())
            throw new IllegalArgumentException("The tribe couldn't be found, spelling perhaps? at line: "+nLine);

        classroom.setTeacher(teacher);
        classroom.setCollege(colleges);
        classroom.setCourse(courses);
        classroom.setTribe(t.get());
        classroom.setGrade(grade);

        teacher.setEmail("");
        teacher.setPositionName("Profesor");

        classroomRepository.save(classroom);

        return teacher;
    }



    private void validateGrade(String grade, int nLine){
        if(!(grade.equals("10")||grade.equals("11")))
            throw new IllegalArgumentException("The grade was incorrect at line: "+nLine);
    }

    private Courses getCourse(String courseString, int nLine){
        Optional<Courses> courses = coursesRepository.findFirstByCourseName(courseString);

        if(!courses.isPresent())
            throw new IllegalArgumentException("The course was not found in the database, at line: "+nLine);

        return courses.get();
    }

    private UserInfo getUser(AuthenticatedUserDto authDto) {
        log.info("Getting userInfo");

        Optional<UserInfo> userInfo = userInfoRepository.findById(authDto.getDocument());
        if(!userInfo.isPresent())
            return null;

        UserType userType = UserType.findByRol(userInfo.get().getRole().getName());

        if(userType!= authDto.getUserType())
            return null;

        return userInfo.get();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            log.info(username);
            AuthenticatedUserDto authDto = new ObjectMapper().readValue(username, AuthenticatedUserDto.class);
            UserInfo user = getUser(authDto);

            if (user == null) {
                log.error("User couldn't be loaded");
                return new User("", "", new ArrayList<>());
            }

            UserType userType = UserType.findByRol(user.getRole().getName());
            boolean userValid = authDto.getUserType().equals(userType) &&
                    authDto.getDocumentType().equals(user.getDocumentType());

            if (!userValid) {
                log.error("User credentials are not correct");
                return new User("", "", new ArrayList<>());
            }

            return new User(user.getDocument(), user.getPassword(),
                    getAuthorities(user.getRole()));
        } catch (IOException e) {
            log.error("Failed to deserialize", e);
            return new User("", "", new ArrayList<>());
        }
    }


    private Collection<? extends GrantedAuthority> getAuthorities(
            Roles role) {
        List<GrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority(role.getName()));

        role.getPrivileges()
                .stream()
                .map(p -> new SimpleGrantedAuthority(p.getName()))
                .forEach(authorities::add);

        return authorities;
    }
}
