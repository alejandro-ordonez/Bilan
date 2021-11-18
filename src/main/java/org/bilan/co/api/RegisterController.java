/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bilan.co.api;

import org.bilan.co.application.user.IRegisterService;
import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.domain.dtos.user.AuthDto;
import org.bilan.co.domain.dtos.user.RegisterUserDto;
import org.bilan.co.domain.enums.UserState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/register")
public class RegisterController {

    @Autowired
    private IRegisterService registerService;

    @PostMapping("/validate")
    public ResponseEntity<ResponseDto<UserState>> validate(@RequestBody AuthDto authDto) {
        ResponseDto<UserState> response = registerService.userExists(authDto);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @PostMapping("/update")
    public ResponseEntity<ResponseDto<UserState>> update(@RequestBody AuthDto authDto) {
        ResponseDto<UserState> response = registerService.updateUser(authDto);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseDto<UserState>> create(@RequestBody RegisterUserDto registerUserDto) {
        ResponseDto<UserState> response = registerService.createUser(registerUserDto);
        return ResponseEntity.status(response.getCode()).body(response);
    }
}
