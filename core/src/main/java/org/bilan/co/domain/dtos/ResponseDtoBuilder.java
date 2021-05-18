package org.bilan.co.domain.dtos;

public class ResponseDtoBuilder<T> {
    private String description;
    private int code;
    private T result;

    public ResponseDtoBuilder<T> setDescription(String description) {
        this.description = description;
        return this;
    }

    public ResponseDtoBuilder<T> setCode(int code) {
        this.code = code;
        return this;
    }

    public ResponseDtoBuilder<T> setResult(T result) {
        this.result = result;
        return this;
    }

    public ResponseDto<T> createResponseDto() {
        return new ResponseDto<>(description, code, result);
    }
}