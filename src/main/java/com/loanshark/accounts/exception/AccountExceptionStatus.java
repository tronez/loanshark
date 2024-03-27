package com.loanshark.accounts.exception;

public enum AccountExceptionStatus {
    STATUS_200("Request processed successfully", "200"),
    STATUS_201("Account created successfully", "201"),
    STATUS_500("An error occurred. Please try again", "500");

    private final String message;
    private final String statusCode;

    AccountExceptionStatus(String message, String statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public String getStatusCode() {
        return statusCode;
    }
}
