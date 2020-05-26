package com.m3alem.m3alem_back_end.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * AuthentificationException
 */
@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class AuthentificationException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public AuthentificationException(String message) {
        super(message);
    }
}
