/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bilan.co.application;

import org.bilan.co.domain.dtos.AuthDto;
import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.domain.enums.UserState;

/**
 * @author Manuel Alejandro
 */
public interface IRegisterService {
    ResponseDto<UserState> userExists(AuthDto authDto);

    ResponseDto<UserState> updateUser(AuthDto authDto);
}
