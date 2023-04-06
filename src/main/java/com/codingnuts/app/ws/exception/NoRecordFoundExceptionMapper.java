package com.codingnuts.app.ws.exception;

import com.codingnuts.app.ws.ui.model.response.ErrorMessage;
import com.codingnuts.app.ws.ui.model.response.ErrorMessages;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class NoRecordFoundExceptionMapper implements ExceptionMapper<NoRecordFoundException> {
    @Override
    public Response toResponse(NoRecordFoundException e) {
        ErrorMessage errorMessage = new ErrorMessage(e.getMessage(), ErrorMessages.NO_RECORD_FOUND.name(), "https://codingnuts.com");
        return Response.status(Response.Status.BAD_REQUEST).entity(errorMessage).build();
    }
}