package com.m3alem.m3alem_back_end.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;

import org.springframework.http.HttpStatus;

/**
 * AvisNotFoundExeption
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class AvisNotFoundExeption extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public AvisNotFoundExeption() {
    }

    public AvisNotFoundExeption(String message) {
        super(message);
    }
}