package org.bilan.co.application.user;

import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.domain.dtos.user.AuthenticatedUserDto;
import org.bilan.co.domain.dtos.user.EnableUser;
import org.bilan.co.domain.dtos.user.UserInfoDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserService extends UserDetailsService {
    AuthenticatedUserDto getUserNameTokenById(AuthenticatedUserDto dataToken);
    ResponseDto<UserInfoDto> getUserInfo(String token);
    ResponseDto<String> updateUserInfo(UserInfoDto userInfoDto, String token);
    ResponseDto<Boolean> enableUser(EnableUser user);
}
