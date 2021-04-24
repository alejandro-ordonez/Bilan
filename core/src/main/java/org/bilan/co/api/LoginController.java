/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bilan.co.api;

import org.bilan.co.ws.simat.client.SimatEstudianteClient;
import org.bilan.co.ws.simat.estudiante.Estudiante;
import org.bilan.co.ws.simat.estudiante.EstudianteNoEncontradoException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Manuel Alejandro
 */
@RestController
public class LoginController {

    private SimatEstudianteClient simatEstudianteClient;

    public LoginController(SimatEstudianteClient simatEstudianteClient) {
        this.simatEstudianteClient = simatEstudianteClient;
    }

    @GetMapping("/login/{studentId}")
    public ResponseEntity<Estudiante> test(@PathVariable String studentId) {
        return this.simatEstudianteClient.getStudent(studentId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
