package org.bilan.co.application;

import org.bilan.co.domain.dtos.AuthenticatedUserDto;
import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.domain.dtos.UserInfoDto;
import org.bilan.co.domain.dtos.UserStatsDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Map;

public interface IUserService extends UserDetailsService {
    AuthenticatedUserDto getUserNameTokenById(AuthenticatedUserDto dataToken);
    ResponseDto<UserInfoDto> getUserInfo(String token);
    ResponseDto<String> updateUserInfo(UserInfoDto userInfoDto, String token);
}
