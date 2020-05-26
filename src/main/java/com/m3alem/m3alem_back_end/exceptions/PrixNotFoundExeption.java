package com.m3alem.m3alem_back_end.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;

import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PrixNotFoundExeption extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public PrixNotFoundExeption(String message) {
        super(message);
    }

}