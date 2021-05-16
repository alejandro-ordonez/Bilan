package org.bilan.co.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.bilan.co.application.IUserService;
import org.bilan.co.application.UserService;
import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.domain.dtos.UserInfoDto;
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

    @GetMapping
    public ResponseEntity<ResponseDto<UserInfoDto>> getUserInfo(@RequestHeader("Authorization") String jwt){
        log.debug("Request received, getting user info, token: "+jwt);
        return userService.getUserInfo();
    }

    @GetMapping("/test")
    public String test(){
        return  "Hello!";
    }
}
