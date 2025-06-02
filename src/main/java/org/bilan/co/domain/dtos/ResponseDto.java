package org.bilan.co.domain.dtos;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseDto<T> {
    private String description;
    private int code;
    private T result;

    public ResponseDto(String description, int code, T result) {
        this.description = description;
        this.code = code;
        this.result = result;
    }

}
