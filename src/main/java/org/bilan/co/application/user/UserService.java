package org.bilan.co.application.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.domain.dtos.ResponseDtoBuilder;
import org.bilan.co.domain.dtos.user.AuthenticatedUserDto;
import org.bilan.co.domain.dtos.user.EnableUser;
import org.bilan.co.domain.dtos.user.UserInfoDto;
import org.bilan.co.domain.entities.*;
import org.bilan.co.domain.enums.DocumentType;
import org.bilan.co.domain.enums.UserType;
import org.bilan.co.infraestructure.persistance.*;
import org.bilan.co.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

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

        UserInfoDto result = new UserInfoDto();
        result.setEmail(user.getEmail());
        result.setName(user.getName());
        result.setDocument(user.getDocument());
        result.setLastName(user.getLastName());
        result.setDocumentType(user.getDocumentType());
        result.setUserType(UserType.findByRol(user.getRole().getName()));

        return new ResponseDtoBuilder<UserInfoDto>().setDescription("Test").setResult(result).setCode(200)
                .createResponseDto();
    }

    @Override
    public ResponseDto<String> updateUserInfo(UserInfoDto userInfoDto, String token) {
        return null;
    }

    @Override
    public ResponseDto<Boolean> enableUser(EnableUser user) {
        return new ResponseDto<>("User state changed", 200,
                userInfoRepository.updateState(user.getDocument(), user.getEnabled()));
    }

    @Override
    public ResponseDto<String> uploadUsersFromFile(MultipartFile file, UserType userType, String token, String campusCodeDane) {

        Colleges colleges;

        if(campusCodeDane == null){
            AuthenticatedUserDto authenticatedUserDto = jwtTokenUtil.getInfoFromToken(token);
            Optional<Classroom> c = classroomRepository.getByTeacher(authenticatedUserDto.getDocument());

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

    private ResponseDto<String> loadUsersFromFile(MultipartFile file, UserType userType, Colleges colleges){

        LineIterator it = null;

        ResponseDto<String> responseDto;

        int nLine = 0;

        try {
            it = FileUtils.lineIterator(file.getResource().getFile(), "UTF-8");

            try {

                while (it.hasNext()) {
                    nLine ++;
                    String line = it.nextLine();
                    String[] user = line.split(",");

                    if(user.length!=5)
                        throw new Exception("The line was in bad format");

                    switch (userType){
                        case Student:
                            processStudent(user, nLine, colleges);
                            break;
                        case Teacher:
                            processTeacher(user, nLine, colleges);
                            break;
                    }

                    // do something with line
                }

            } catch (Exception e) {
                String message = "One of the lines was incorrect, it didn't match the expected columns or one of the" +
                        "arguments were incorrect: " + e.getMessage();
                log.error(message);
                responseDto = new ResponseDto<>(message, 500, "");

            } finally {
                LineIterator.closeQuietly(it);
            }

        } catch (IOException e) {
            log.error("Failed to extract the file from request");
        }

        return responseDto;
    }


    private void processTeacher(String[] user, int nLine, Colleges colleges) throws IllegalArgumentException{
        String document = user[0];

        if(!teachersRepository.existsById(document))
            throw new IllegalArgumentException("Teacher not found");


        DocumentType documentType = DocumentType.valueOf(user[1]);
        String grade = user[2];

        if(!(grade.equals("10")||grade.equals("11")))
            throw new IllegalArgumentException("The grade was incorrect at line: "+nLine);

        String courseString = user[3];
        Optional<Courses> courses = coursesRepository.findByCourseName(courseString);

        if(!courses.isPresent())
            throw new IllegalArgumentException("The course was not found in the database, at line: "+nLine);

        String tribeName = user[4];


        Classroom classroom = new Classroom();

        Teachers teacher = new Teachers();
        teacher.setDocument(document);

        Optional<Tribes> t = tribesRepository.getByName(tribeName);

        if(!t.isPresent())
            throw new IllegalArgumentException("The tribe couldn't be found, spelling perhaps? at line: "+nLine);

        classroom.setTeacher(teacher);
        classroom.setCollege(colleges);
        classroom.setCourse(courses.get());
        classroom.setTribe(t.get());
        classroom.setGrade(grade);
    }

    private void processStudent(String[] user, int nLine, Colleges colleges) {



    }


    private UserInfo getUser(AuthenticatedUserDto authDto) {
        log.info("Getting userInfo");
        switch (authDto.getUserType()) {
            case Teacher:
                return teachersRepository.findById(authDto.getDocument()).orElse(null);
            case Student:
                return studentsRepository.findById(authDto.getDocument()).orElse(null);
            case Min:
                return minUserRepository.findById(authDto.getDocument()).orElse(null);
            default:
                return null;
        }
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
