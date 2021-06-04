/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bilan.co.api;

import org.bilan.co.application.ILoginService;
import org.bilan.co.domain.dtos.AuthDto;
import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.ws.simat.client.SimatEstudianteClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth/login")
public class LoginController {

    private final SimatEstudianteClient simatEstudianteClient;
    private final ILoginService loginService;

    public LoginController(SimatEstudianteClient simatEstudianteClient, ILoginService loginService) {
        this.simatEstudianteClient = simatEstudianteClient;
        this.loginService = loginService;
    }

    @PostMapping
    public ResponseEntity<ResponseDto<String>> login(@Valid @RequestBody AuthDto login) {
        return loginService.doLogin(login);
    }
}
