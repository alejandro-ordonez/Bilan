/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bilan.co.application.user;

import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.domain.dtos.user.AuthDto;
import org.springframework.http.ResponseEntity;

public interface ILoginService {
    /**
     * Creates a JWT token to validate if the session is valid
     *
     * @param loginInfo Login info containing the required data to validate the user.
     * @return Returns a String containing the JWT
     */
    ResponseEntity<ResponseDto<String>> doLogin(AuthDto loginInfo);
}
