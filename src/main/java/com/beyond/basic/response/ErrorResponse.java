package com.beyond.basic.response;

import lombok.Getter;

@Getter
public class ErrorResponse extends CustomResponse {

    private String errorMessage;

    public ErrorResponse(int stautsCode, String errorMessage) {
        super(stautsCode);
        this.errorMessage = errorMessage;
    }
}
