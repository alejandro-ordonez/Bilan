package org.bilan.co.api;

import lombok.extern.slf4j.Slf4j;
import org.bilan.co.application.user.IUserService;
import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.domain.dtos.user.EnableUser;
import org.bilan.co.domain.dtos.user.UserInfoDto;
import org.bilan.co.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/enable")
    public ResponseEntity<ResponseDto<Boolean>> update(@RequestBody EnableUser user){
        return ResponseEntity.ok(userService.enableUser(user));
    }

}
