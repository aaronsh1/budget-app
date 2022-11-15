package com.budget.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalTime;

public class ApiExceptionResponse {

    private LocalTime timestamp;
    private int status;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
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

    public void buildErrorResponse(Exception exception, WebRequest request, HttpStatus status){
        this.timestamp = LocalTime.now();
        this.exception = exception.getClass();
        this.status = status.value();
        this.message = exception.getMessage();
        this.path = request.getDescription(false);
    }

    //endregion

}
