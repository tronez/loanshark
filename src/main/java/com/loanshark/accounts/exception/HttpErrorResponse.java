package com.loanshark.accounts.exception;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record HttpErrorResponse(String apiPath, HttpStatus errorCode, String errorMessage, LocalDateTime errorTime) {
    public static HttpErrorResponse createWithCurrentTime(String apiPath, HttpStatus errorCode, String errorMessage) {
        return new HttpErrorResponse(apiPath, errorCode, errorMessage, LocalDateTime.now());
    }
}
