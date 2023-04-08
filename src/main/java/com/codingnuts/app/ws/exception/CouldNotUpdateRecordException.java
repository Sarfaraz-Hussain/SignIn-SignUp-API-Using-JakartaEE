package com.codingnuts.app.ws.exception;

public class CouldNotUpdateRecordException extends RuntimeException {
    private static final long serialVersionUID = 7172505944284633973L;

    public CouldNotUpdateRecordException(String message) {
        super(message);
    }
}
