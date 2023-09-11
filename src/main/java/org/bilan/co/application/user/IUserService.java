package org.bilan.co.application.user;

import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.domain.dtos.common.PagedResponse;
import org.bilan.co.domain.dtos.user.AuthenticatedUserDto;
import org.bilan.co.domain.dtos.user.EnableUser;
import org.bilan.co.domain.dtos.user.UploadFromFileResultDto;
import org.bilan.co.domain.dtos.user.UserInfoDto;
import org.bilan.co.domain.entities.Colleges;
import org.bilan.co.domain.entities.Students;
import org.bilan.co.domain.entities.Teachers;
import org.bilan.co.domain.enums.UserType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

public interface IUserService extends UserDetailsService {
    AuthenticatedUserDto getUserNameTokenById(AuthenticatedUserDto dataToken);

    ResponseDto<UserInfoDto> getUserInfo(String token);

    ResponseDto<String> updateUserInfo(UserInfoDto userInfoDto, String token);

    ResponseDto<Boolean> enableUser(EnableUser user);

    ResponseDto<UploadFromFileResultDto> uploadUsersFromFile(MultipartFile file, UserType userType, String token, String campusCodeDane);

    Students processStudent(String[] user, int nLine, Colleges colleges) throws Exception;

    Teachers processTeacher(String[] user, int nLine, Colleges colleges) throws Exception;

    ResponseDto<PagedResponse<UserInfoDto>> getUsersAdmin(Integer nPage, String partialDocument, String jwt);
}
