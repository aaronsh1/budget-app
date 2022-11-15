package com.budget.api.exception;

public class ApiException {
    private String message;
    private Exception exception;

    public ApiException(String message, Exception exception) {
        this.message = message;
        this.exception = exception;
    }

    public ApiException() {
    }
}
