/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bilan.co.application;

import org.bilan.co.domain.dtos.LoginDto;
import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

/**
 *
 * @author Manuel Alejandro
 */
@Service
public class LoginService implements ILoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public ResponseEntity<ResponseDto<String>> DoLogin(LoginDto loginInfo) {

        String jwt = jwtTokenUtil.generateToken(loginInfo);

        return  ResponseEntity.ok(
                new ResponseDto<>("Authentication Successful", 200, jwt));
    }
}
