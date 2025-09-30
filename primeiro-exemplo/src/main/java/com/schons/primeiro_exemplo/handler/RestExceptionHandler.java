package com.schons.primeiro_exemplo.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.schons.primeiro_exemplo.model.error.ErrorMessage;
import com.schons.primeiro_exemplo.model.exception.ResourceNegativeException;
import com.schons.primeiro_exemplo.model.exception.ResourceNotFoundException;

@ControllerAdvice
public class RestExceptionHandler {
    
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundEntityException(ResourceNotFoundException ex){
        ErrorMessage error = new ErrorMessage("Not Found", 404, ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceNegativeException.class)
    public ResponseEntity<?> handleResourceNegativeException(ResourceNegativeException rn){
        ErrorMessage error = new ErrorMessage("Less then 1", HttpStatus.NOT_ACCEPTABLE.value(), rn.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_ACCEPTABLE);
    }
}
