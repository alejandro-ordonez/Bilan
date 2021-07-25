package org.bilan.co.api;

import lombok.extern.slf4j.Slf4j;
import org.bilan.co.application.IUserService;
import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.domain.dtos.UserInfoDto;
import org.bilan.co.domain.dtos.UserStatsDto;
import org.bilan.co.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/stats")
    public ResponseEntity<ResponseDto<UserStatsDto>> getStats(@RequestHeader(Constants.AUTHORIZATION) String jwt){
        return  ResponseEntity.ok(userService.getUserStats(jwt));
    }
}
