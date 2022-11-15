package com.budget.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ApiExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiException handleResourceNotFound(ResourceNotFoundException exception){
        return new ApiException("Could not find the resource with the specified id.", exception);
    }

    @ResponseBody
    @ExceptionHandler(value = EmptyResourceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiException handleEmptyResource(EmptyResourceException exception){
        return new ApiException("The resource entered was empty.", exception);
    }

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public ApiException handleGeneral(Exception exception){
        return new ApiException("The system encountered an error.", exception);
    }
}
