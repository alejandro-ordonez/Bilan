package org.bilan.co.api;

import lombok.extern.slf4j.Slf4j;
import org.bilan.co.application.user.IUserService;
import org.bilan.co.domain.dtos.BasicInfo;
import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.domain.dtos.common.PagedResponse;
import org.bilan.co.domain.dtos.user.EnableUser;
import org.bilan.co.domain.dtos.user.UserInfoDto;
import org.bilan.co.domain.enums.UserType;
import org.bilan.co.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping("/info")
    public ResponseEntity<ResponseDto<UserInfoDto>> getUserInfo(@RequestHeader(Constants.AUTHORIZATION) String jwt){
        return ResponseEntity.ok(userService.getUserInfo(jwt));
    }

    @PostMapping("/info")
    public ResponseEntity<ResponseDto<String>> updateUserInfo(@RequestBody UserInfoDto userInfoDto, @RequestHeader(Constants.AUTHORIZATION) String jwt){
        return ResponseEntity.ok(userService.updateUserInfo(userInfoDto, jwt));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/enable")
    public ResponseEntity<ResponseDto<Boolean>> update(@RequestBody EnableUser user){
        return ResponseEntity.ok(userService.enableUser(user));
    }

    @PreAuthorize("hasAnyAuthority('DIRECT_TEACHER', 'ADMIN')")
    @PostMapping("/load")
    public ResponseEntity<ResponseDto<String>> uploadUsersFromFile(@RequestPart("file") MultipartFile file,
                                                                   @RequestParam("userType") UserType userType,
                                                                   @RequestParam(value = "campusCodeDane", required = false) String campusCode,
                                                                   @RequestHeader(Constants.AUTHORIZATION) String jwt){

        ResponseDto<String> result = userService.uploadUsersFromFile(file, userType, jwt, campusCode);
        return ResponseEntity.status(result.getCode()).body(result);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping
    public ResponseEntity<ResponseDto<PagedResponse<UserInfoDto>>> getUsers(
            @RequestParam("page") Integer nPage,
            @RequestParam(value = "partialDocument", required = false) String partialDocument,
            @RequestHeader(Constants.AUTHORIZATION) String jwt){

        ResponseDto<PagedResponse<UserInfoDto>> users = userService.getUsersAdmin(nPage,partialDocument, jwt);
        return ResponseEntity.status(users.getCode()).body(users);
    }

}
