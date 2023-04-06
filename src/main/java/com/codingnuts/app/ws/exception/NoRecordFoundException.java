package com.codingnuts.app.ws.exception;

public class NoRecordFoundException extends RuntimeException{
    private static final long serialVersionUID = -3866420050971738491L;
    public NoRecordFoundException(String message){
        super(message);
    }
}
