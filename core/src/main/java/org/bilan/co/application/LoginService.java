/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bilan.co.application;

import org.bilan.co.domain.dtos.LoginDto;
import org.springframework.stereotype.Service;

/**
 *
 * @author Manuel Alejandro
 */
@Service
public class LoginService implements ILoginService {


    @Override
    public String DoLogin(LoginDto loginInfo) {
        return  "TESTJWT";
    }
}
