/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bilan.co.api;

import org.bilan.co.application.ILoginService;
import org.bilan.co.domain.dtos.LoginDto;
import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.ws.simat.client.SimatEstudianteClient;
import org.bilan.co.ws.simat.estudiante.Estudiante;
import org.bilan.co.ws.simat.estudiante.EstudianteNoEncontradoException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/phase1")
    public ResponseEntity<ResponseDto<String>> Login(@Valid @RequestBody LoginDto login) {
        return loginService.DoLogin(login);
        /*return this.simatEstudianteClient.getStudent(studentId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());*/
    }
}
