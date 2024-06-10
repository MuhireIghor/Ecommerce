package com.ne.template.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.text.MessageFormat;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
@Getter
@AllArgsConstructor
public class ResourceNotFoundException extends Exception {

    private String message = "exceptions.notFound";
    private Object[] args;


    public ResourceNotFoundException(Object ...args){
        this.args = args;
    }
}


