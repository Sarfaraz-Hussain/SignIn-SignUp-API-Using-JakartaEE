package com.codingnuts.app.ws.exception;

public class CouldNotCreateRecordException extends RuntimeException {

    private static final long serialVersionUID = 4656648658051864603L;

    public CouldNotCreateRecordException(String message) {
        super(message);
    }
}
