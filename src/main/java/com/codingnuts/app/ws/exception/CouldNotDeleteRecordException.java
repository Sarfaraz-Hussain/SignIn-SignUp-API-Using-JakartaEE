package com.codingnuts.app.ws.exception;

public class CouldNotDeleteRecordException extends RuntimeException {

    private static final long serialVersionUID = 7306059162892092051L;

    public CouldNotDeleteRecordException(String message) {
        super(message);
    }
}
