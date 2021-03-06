package com.m3alem.m3alem_back_end.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public UserNotFoundException() {
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
