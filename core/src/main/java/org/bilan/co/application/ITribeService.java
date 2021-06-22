package org.bilan.co.application;

import org.bilan.co.domain.dtos.AuthDto;
import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.domain.dtos.TribeDto;
import org.springframework.http.ResponseEntity;

public interface ITribeService {

    ResponseEntity<ResponseDto<TribeDto>> get(AuthDto loginInfo);
}
