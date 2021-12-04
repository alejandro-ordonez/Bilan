package org.bilan.co.domain.dtos;

public class ResponseDto<T> {
    private String description;
    private int Code;
    private T result;

    public ResponseDto(String description, int code, T result) {
        this.description = description;
        Code = code;
        this.result = result;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCode() {
        return Code;
    }

    public void setCode(int code) {
        Code = code;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
