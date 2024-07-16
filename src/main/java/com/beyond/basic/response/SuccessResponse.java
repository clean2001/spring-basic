package com.beyond.basic.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class SuccessResponse<T> {
    int statusCode;
    String statusMessage;
    T result;

    public SuccessResponse(HttpStatus httpStatus, String statusMessage, T result) {
        this.statusCode = httpStatus.value();
        this.statusMessage = httpStatus.getReasonPhrase();
        this.result = result;
    }
}
