package com.ShopAll.Methaporce.ExceptionHandler;

import com.ShopAll.Methaporce.Exception.UserException;

import com.ShopAll.Methaporce.Exception.ValidationErrorResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
@ExceptionHandler(UserException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleUserException(UserException ex){
    return new ErrorResponse("El servidor dice:",ex.getMessage());
}

@ExceptionHandler(MethodArgumentNotValidException.class)
@ResponseStatus(HttpStatus.BAD_REQUEST)
public ValidationErrorResponse handleValidationException(MethodArgumentNotValidException ex){

    List<String> errorsMessage=ex.getBindingResult().getFieldErrors()
            .stream()
            .map(FieldError::getDefaultMessage)
            .collect(Collectors.toList());
    return  new ValidationErrorResponse("Algunos Datos son incorrectos", errorsMessage);
}
    @JsonInclude
    @Data
    public static class ErrorResponse{
        private String error;
        private String message;

        public ErrorResponse(String error,String message){
            this.error=error;
            this.message=message;
        }
    }
}
