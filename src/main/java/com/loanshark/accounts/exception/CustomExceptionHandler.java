package com.loanshark.accounts.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, headers, status);
    }


    @ExceptionHandler(CustomerAlreadyExistsException.class)
    public ResponseEntity<HttpErrorResponse> handleCustomerAlreadyExists(CustomerAlreadyExistsException exception,
                                                                         WebRequest webRequest) {
        var errorResponseDto = HttpErrorResponse.createWithCurrentTime(
                webRequest.getDescription(false),
                HttpStatus.BAD_REQUEST,
                exception.getMessage());
        return new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<HttpErrorResponse> handleResourceNotFoundException(ResourceNotFoundException exception,
                                                                             WebRequest webRequest) {
        var errorResponseDto = HttpErrorResponse.createWithCurrentTime(
                webRequest.getDescription(false),
                HttpStatus.NOT_FOUND,
                exception.getMessage());
        return new ResponseEntity<>(errorResponseDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<HttpErrorResponse> handleGlobalException(Exception exception, WebRequest webRequest) {
        var errorResponseDto = HttpErrorResponse.createWithCurrentTime(
                webRequest.getDescription(false),
                HttpStatus.INTERNAL_SERVER_ERROR,
                exception.getMessage());
        return new ResponseEntity<>(errorResponseDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
