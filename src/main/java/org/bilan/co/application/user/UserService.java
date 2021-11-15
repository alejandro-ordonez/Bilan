package org.bilan.co.application.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.bilan.co.domain.dtos.*;
import org.bilan.co.domain.entities.Privileges;
import org.bilan.co.domain.entities.Roles;
import org.bilan.co.domain.entities.UserInfo;
import org.bilan.co.infraestructure.persistance.*;
import org.bilan.co.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
    private JwtTokenUtil jwtTokenUtil;

    public AuthenticatedUserDto getUserNameTokenById(AuthenticatedUserDto dataToken) {

        UserInfo user = getUser(dataToken);

        if (user == null)
            return new AuthenticatedUserDto();

        return new AuthenticatedUserDto(user.getDocument(), dataToken.getUserType(), user.getDocumentType());
    }


    @Override
    public ResponseDto<UserInfoDto> getUserInfo(String token) {

        AuthenticatedUserDto userAuthenticated = jwtTokenUtil.getInfoFromToken(token);
        Object user = getUser(userAuthenticated);

        ObjectMapper objectMapper = new ObjectMapper();
        UserInfoDto result = objectMapper.convertValue(user, UserInfoDto.class);

        return new ResponseDtoBuilder<UserInfoDto>().setDescription("Test").setResult(result).setCode(200)
                .createResponseDto();
    }

    @Override
    public ResponseDto<String> updateUserInfo(UserInfoDto userInfoDto, String token) {
        return null;
    }

    @Override
    public ResponseDto<Boolean> enableUser(EnableUser user) {

        switch (user.getUserType()){
            case Teacher:
                teachersRepository.updateState(user.getDocument(), user.getEnabled());
                break;
            case Student:
                studentsRepository.updateState(user.getDocument(), user.getEnabled());
                break;
            case Min:
                minUserRepository.updateState(user.getDocument(), user.getEnabled());
                break;
            case Admin:
                break;
        }
        return null;
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

            if(user == null){
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

        return getGrantedAuthorities(getPrivileges(role));
    }

    private List<String> getPrivileges(Roles role) {

        List<String> privileges = new ArrayList<>();

        privileges.add(role.getName());
        List<Privileges> collection = new ArrayList<>(role.getPrivileges());

        for (Privileges item : collection) {
            privileges.add(item.getName());
        }

        return privileges;
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }

}
