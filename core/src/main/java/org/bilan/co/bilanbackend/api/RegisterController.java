/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bilan.co.bilanbackend.api;

import org.bilan.co.bilanbackend.application.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Manuel Alejandro
 */
@RestController
@RequestMapping("/register-user")
public class RegisterController {
    
    @Autowired
    private RegisterService registerService;
    
    @GetMapping
    public String test(){
        return "Hey from register";
    }
    
    
}
