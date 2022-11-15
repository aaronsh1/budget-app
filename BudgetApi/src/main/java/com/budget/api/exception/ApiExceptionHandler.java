package com.budget.api.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiExceptionResponse handleResourceNotFound(ResourceNotFoundException exception, WebRequest request){
        ApiExceptionResponse responseToReturn = new ApiExceptionResponse();
        responseToReturn.buildErrorResponse(exception, request, HttpStatus.NOT_FOUND);
        return responseToReturn;
    }

    @ResponseBody
    @ExceptionHandler(value = EmptyResourceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiExceptionResponse handleEmptyResource(EmptyResourceException exception, WebRequest request){
        ApiExceptionResponse responseToReturn = new ApiExceptionResponse();
        responseToReturn.buildErrorResponse(exception, request, HttpStatus.BAD_REQUEST);
        return responseToReturn;
    }

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public ApiExceptionResponse handleGeneral(Exception exception, WebRequest request){
        ApiExceptionResponse responseToReturn = new ApiExceptionResponse();
        responseToReturn.buildErrorResponse(exception, request, HttpStatus.SERVICE_UNAVAILABLE);
        return responseToReturn;
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
                                                                         HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiExceptionResponse responseToReturn = new ApiExceptionResponse();
        responseToReturn.buildErrorResponse(ex, request, HttpStatus.METHOD_NOT_ALLOWED);
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(responseToReturn);
    }
}
