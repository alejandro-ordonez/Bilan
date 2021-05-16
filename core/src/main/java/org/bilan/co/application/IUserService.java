package org.bilan.co.application;

import org.bilan.co.domain.dtos.AuthenticatedUserDto;
import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.domain.dtos.UserInfoDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Map;

public interface IUserService extends UserDetailsService {
    AuthenticatedUserDto getUserNameTokenById(Map<String, Object> dataToken);
    ResponseDto<UserInfoDto> getUserInfo(String token);
}
