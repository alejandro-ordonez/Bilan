/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bilan.co.bilanbackend.application;

import org.bilan.co.bilanbackend.data.StudentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Manuel Alejandro
 */
@Service
public class RegisterService implements IRegisterService {
    
    /**
     * Reference to the StuedenRepository
     */
    @Autowired
    private StudentsRepository repository;
    
    
    
}
