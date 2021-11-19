package org.bilan.co.utils;

import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.bilan.co.domain.dtos.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(SizeLimitExceededException.class)
    @ResponseStatus(value = HttpStatus.PAYLOAD_TOO_LARGE)
    public ResponseEntity<ResponseDto<String>> handleMultipartException(SizeLimitExceededException e) {
        return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                .body(new ResponseDto<>(HttpStatus.PAYLOAD_TOO_LARGE.getReasonPhrase(),
                        HttpStatus.PAYLOAD_TOO_LARGE.value(), "File too large"));
    }
}
