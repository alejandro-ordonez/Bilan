package org.bilan.co.application.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.domain.dtos.ResponseDtoBuilder;
import org.bilan.co.domain.dtos.user.AuthenticatedUserDto;
import org.bilan.co.domain.dtos.user.EnableUser;
import org.bilan.co.domain.dtos.user.UserInfoDto;
import org.bilan.co.domain.entities.Roles;
import org.bilan.co.domain.entities.UserInfo;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

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
