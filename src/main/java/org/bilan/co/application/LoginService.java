/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bilan.co.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.bilan.co.domain.dtos.AuthDto;
import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.domain.dtos.ResponseDtoBuilder;
import org.bilan.co.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LoginService implements ILoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public ResponseEntity<ResponseDto<String>> doLogin(AuthDto loginInfo) {

        try {
            authenticate(new ObjectMapper().writeValueAsString(loginInfo), loginInfo.getPassword());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    new ResponseDtoBuilder<String>()
                            .setDescription("Authentication Failed")
                            .setCode(400)
                            .setResult(null)
                            .createResponseDto());
        }
        String jwt = jwtTokenUtil.generateToken(loginInfo);

        return ResponseEntity.ok(
                new ResponseDtoBuilder<String>()
                        .setDescription("Authentication Successful")
                        .setCode(200).setResult(jwt)
                        .createResponseDto());
    }

    private void authenticate(String data, String password) throws Exception {
        log.info(password);
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(data, password));
        } catch (DisabledException e) {
            log.error("Failed authentication", e);
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            log.error("Failed authentication", e);
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}