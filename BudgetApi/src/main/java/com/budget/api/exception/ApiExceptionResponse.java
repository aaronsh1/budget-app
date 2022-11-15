package com.budget.api.exception;

import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalTime;

public class ApiExceptionResponse {

    private LocalTime timestamp;
    private HttpStatus status;
    private String message;
    private Class exception;
    private String path;

    public ApiExceptionResponse() {
    }

    //region getters&setters

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Class getException() {
        return exception;
    }

    public void setException(Class exception) {
        this.exception = exception;
    }

    public LocalTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalTime timestamp) {
        this.timestamp = timestamp;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
    //endregion

    //region methods

    public void buildErrorResponse(Exception exception, HttpServletRequest request, HttpStatus status){
        this.timestamp = LocalTime.now();
        this.exception = exception.getClass();
        this.status = status;
        this.message = exception.getMessage();
        this.path = request.getServletPath();
    }

    //endregion

}
