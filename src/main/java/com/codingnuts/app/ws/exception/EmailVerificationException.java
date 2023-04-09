package com.codingnuts.app.ws.exception;

public class EmailVerificationException extends RuntimeException {

    private static final long serialVersionUID = -7118485928913271818L;

    public EmailVerificationException(String message) {
        super(message);
    }
}
