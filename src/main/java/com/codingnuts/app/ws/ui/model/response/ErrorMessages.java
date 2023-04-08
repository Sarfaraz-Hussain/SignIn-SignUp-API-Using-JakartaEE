package com.codingnuts.app.ws.ui.model.response;

import net.bytebuddy.dynamic.DynamicType;

public enum ErrorMessages {
    MISSING_REQUIRED_FIELD("Missing Required Field. Please check documentation for required fields"),
    RECORD_ALREADY_EXISTS("Record already exists"),
    INTERNAL_SERVER_ERROR("Inter Server Error"),
    AUTHENTICATION_FAILED("Authentication failed"),
    NO_RECORD_FOUND("Record with provided id is not found"),

    COULD_NOT_UPDATE_RECORD("Could not update record") ;
    private String errorMessage;
    ErrorMessages(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
