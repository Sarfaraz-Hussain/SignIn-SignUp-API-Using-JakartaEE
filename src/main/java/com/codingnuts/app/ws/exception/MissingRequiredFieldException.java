package com.codingnuts.app.ws.exception;

public class MissingRequiredFieldException extends RuntimeException{
    private static final long serialVersionUID = 9180807694599483182L;
    public MissingRequiredFieldException(String message) {
        super(message);
    }
}
