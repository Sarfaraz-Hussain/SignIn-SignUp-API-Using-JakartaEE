package com.codingnuts.app.ws.exception;

public class AuthenticationException extends RuntimeException {

    private static final long serialVersionUID = -1295774051320785485L;

    public AuthenticationException(String message) {
        super(message);
    }
}
