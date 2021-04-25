/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bilan.co.api;

import org.bilan.co.domain.dtos.RegisterDto;
import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.domain.dtos.enums.UserState;
import org.bilan.co.application.IRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author Manuel Alejandro
 */
@RestController
@RequestMapping("/auth/register")
public class RegisterController {

    @Autowired
    private IRegisterService registerService;
    
    @PostMapping("/validate")
    public ResponseEntity<ResponseDto<UserState>> validate(@RequestBody RegisterDto registerDto){
        ResponseDto response = registerService.userExists(registerDto);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @PostMapping("/update")
    public ResponseEntity<ResponseDto<UserState>> update(@RequestBody RegisterDto registerDto){

        return null;
    }
}
