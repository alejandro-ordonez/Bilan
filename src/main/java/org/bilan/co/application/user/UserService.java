package org.bilan.co.application.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
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

        return new ResponseDtoBuilder<UserInfoDto>().setDescription("Test").setResult(result).setCode(200)
                .createResponseDto();
    }

    private UserInfoDto parseUser(UserInfo user){
        UserInfoDto result = new UserInfoDto();
        result.setEmail(user.getEmail());
        result.setName(user.getName());
        result.setDocument(user.getDocument());
        result.setLastName(user.getLastName());
        result.setDocumentType(user.getDocumentType());
        result.setGrantedAuthorities(getAuthorities(user.getRole()));
        result.setIsEnabled(user.getIsEnabled());
        result.setUserType(UserType.findByRol(user.getRole().getName()));

        return result;
    }

    @Override
    public ResponseDto<String> updateUserInfo(UserInfoDto userInfoDto, String token) {
        return null;
    }

    @Override
    public ResponseDto<Boolean> enableUser(EnableUser user) {
        userInfoRepository.updateState(user.getDocument(), user.getEnabled());
        return new ResponseDto<>("User state changed", 200, user.getEnabled());
    }

    @Override
    public ResponseDto<String> uploadUsersFromFile(MultipartFile file, UserType userType, String token, String campusCodeDane) {

        Colleges colleges;

        if(campusCodeDane == null){
            AuthenticatedUserDto authenticatedUserDto = jwtTokenUtil.getInfoFromToken(token);
            Optional<Classroom> c = classroomRepository.findFirstByTeacher(authenticatedUserDto.getDocument());

            if(!c.isPresent()){
                String message = "The directive teacher does not have a college linked";
                throw new IllegalArgumentException(message);
            }

            colleges = c.get().getCollege();
        }
        else {
            colleges = collegesRepository.collegeByCampusCodeDane(campusCodeDane);
        }

        return loadUsersFromFile(file, userType, colleges);

    }

    @Override
    public ResponseDto<PagedResponse<UserInfoDto>> getUsersAdmin(Integer nPage, String partialDocument) {

        Page<UserInfo> query;

        if(partialDocument == null)
                query = userInfoRepository.getUsersAdmin(PageRequest.of(nPage, 10));

        else{
            query = userInfoRepository.searchUsersWithDocument(PageRequest.of(nPage, 10), partialDocument);
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

    private ResponseDto<String> loadUsersFromFile(MultipartFile file, UserType userType, Colleges colleges){

        LineIterator it = null;

        ResponseDto<String> responseDto;

        int nLine = 0;

        try {
            File fileToUpload = new File(Objects.requireNonNull(file.getOriginalFilename()));
            FileUtils.copyToFile(file.getInputStream(), fileToUpload);
            it = FileUtils.lineIterator(fileToUpload, "UTF-8");

            try {
                String line = it.nextLine();
                while (it.hasNext()) {
                    nLine ++;
                    line = it.nextLine();
                    String[] user = line.split(",");

                    switch (userType){
                        case Student:
                            processStudent(user, nLine, colleges);
                            break;
                        case Teacher:
                            processTeacher(user, nLine, colleges);
                            break;
                    }
                }

            } catch (Exception e) {
                String message = "One of the lines was incorrect, it didn't match the expected columns or one of the" +
                        "arguments were incorrect: " + e.getMessage();
                log.error(message);
                return new ResponseDto<>(message, 500, "");

            } finally {
                LineIterator.closeQuietly(it);
            }

        } catch (IOException e) {
            String message = "Failed to extract the file from request";
            log.error(message, e);
            return new ResponseDto<>(message, 500, "");
        }

        return new ResponseDto<>("The users were added successfully", 200, "");
    }


    private void processTeacher(String[] user, int nLine, Colleges colleges) throws Exception {
        if(user.length!=5)
            throw new Exception("The line was in bad format");

        String document = user[0];

        if(!teachersRepository.existsById(document))
            throw new IllegalArgumentException("Teacher not found: "+ document);


        DocumentType documentType = DocumentType.valueOf(user[1]);
        String grade = user[2];

        validateGrade(grade, nLine);

        String courseString = user[3];

        Courses courses = getCourse(courseString, nLine);

        String tribeName = user[4];

        Classroom classroom = new Classroom();

        Teachers teacher = new Teachers();
        teacher.setDocument(document);

        Optional<Tribes> t = tribesRepository.getByName(tribeName);

        if(!t.isPresent())
            throw new IllegalArgumentException("The tribe couldn't be found, spelling perhaps? at line: "+nLine);

        classroom.setTeacher(teacher);
        classroom.setCollege(colleges);
        classroom.setCourse(courses);
        classroom.setTribe(t.get());
        classroom.setGrade(grade);

        classroomRepository.save(classroom);
    }

    private void processStudent(String[] user, int nLine, Colleges colleges) throws Exception {
        if(user.length!=6)
            throw new Exception("The line was in bad format, at: "+nLine);

        String document = user[0];
        DocumentType documentType = DocumentType.valueOf(user[1]);
        String name = user[2];
        String lastName = user[3];
        String grade = user[4];
        String courseString = user[5];

        validateGrade(grade, nLine);

        Courses courses = getCourse(courseString, nLine);

        Roles roles = new Roles();
        roles.setId(1);

        Students s = new Students();
        s.setDocument(document);
        s.setDocumentType(documentType);
        s.setName(name);
        s.setLastName(lastName);
        s.setGrade(grade);
        s.setCourses(courses);
        s.setRole(roles);
        s.setColleges(colleges);
        s.setPassword(passwordEncoder.encode(document));
        s.setIsEnabled(true);
        s.setConfirmed(false);

        studentsRepository.save(s);
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
