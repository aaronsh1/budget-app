package com.budget.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiExceptionResponse handleResourceNotFound(ResourceNotFoundException exception, HttpServletRequest request){
        ApiExceptionResponse responseToReturn = new ApiExceptionResponse();
        responseToReturn.buildErrorResponse(exception, request, HttpStatus.NOT_FOUND);
        return responseToReturn;
    }

    @ResponseBody
    @ExceptionHandler(value = EmptyResourceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiExceptionResponse handleEmptyResource(EmptyResourceException exception, HttpServletRequest request){
        ApiExceptionResponse responseToReturn = new ApiExceptionResponse();
        responseToReturn.buildErrorResponse(exception, request, HttpStatus.BAD_REQUEST);
        return responseToReturn;
    }

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public ApiExceptionResponse handleGeneral(Exception exception, HttpServletRequest request){
        ApiExceptionResponse responseToReturn = new ApiExceptionResponse();
        responseToReturn.buildErrorResponse(exception, request, HttpStatus.SERVICE_UNAVAILABLE);
        return responseToReturn;
    }
}
