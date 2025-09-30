package com.schons.primeiro_exemplo.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE)
public class ResourceNegativeException extends RuntimeException {

    public ResourceNegativeException(String mensagem){
        super(mensagem);
    }
}