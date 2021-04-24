/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bilan.co.application;

import org.bilan.co.domain.dtos.LoginDto;
import org.bilan.co.domain.dtos.ResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

public interface ILoginService {
    /**
     * Creates a JWT token to validate if the session is valid
     * @param loginInfo Login info containing the required data to validate the user.
     * @return Returns a String containing the JWT
     */
    ResponseEntity<ResponseDto<String>> DoLogin(LoginDto loginInfo);
}
