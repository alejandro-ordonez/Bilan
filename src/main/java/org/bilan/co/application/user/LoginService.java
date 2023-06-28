/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bilan.co.application.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.domain.dtos.ResponseDtoBuilder;
import org.bilan.co.domain.dtos.user.AuthDto;
import org.bilan.co.utils.JwtTokenUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LoginService implements ILoginService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;

    public LoginService(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    public ResponseEntity<ResponseDto<String>> doLogin(AuthDto loginInfo) {

        try {
            authenticate(new ObjectMapper().writeValueAsString(loginInfo), loginInfo.getPassword());
        } catch (BadCredentialsException e) {
            return ResponseEntity.badRequest().body(
                    new ResponseDtoBuilder<String>()
                            .setDescription("Authentication Failed, bad credentials")
                            .setCode(400)
                            .setResult("")
                            .createResponseDto());
        } catch (DisabledException e){
            return ResponseEntity.badRequest().body(
                    new ResponseDtoBuilder<String>()
                            .setDescription("Authentication Failed, the user is disabled")
                            .setCode(400)
                            .setResult("")
                            .createResponseDto());
        } catch (JsonProcessingException e){
            return ResponseEntity.badRequest().body(
                    new ResponseDtoBuilder<String>()
                            .setDescription("Authentication Failed, error extracting the data")
                            .setCode(400)
                            .setResult("")
                            .createResponseDto());
        }
        String jwt = jwtTokenUtil.generateToken(loginInfo);
        return ResponseEntity.ok(
                new ResponseDtoBuilder<String>()
                        .setDescription("Authentication Successful")
                        .setCode(200).setResult(jwt)
                        .createResponseDto());
    }

    private void authenticate(String data, String password)  {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(data, password));
    }
}
