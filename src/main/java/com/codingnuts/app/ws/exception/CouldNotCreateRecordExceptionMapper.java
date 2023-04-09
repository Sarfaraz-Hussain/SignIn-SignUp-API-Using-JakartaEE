package com.codingnuts.app.ws.exception;

import com.codingnuts.app.ws.ui.model.response.ErrorMessage;
import com.codingnuts.app.ws.ui.model.response.ErrorMessages;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class CouldNotCreateRecordExceptionMapper implements ExceptionMapper<CouldNotCreateRecordException> {
    @Override
    public Response toResponse(CouldNotCreateRecordException e) {
        ErrorMessage errorMessage = new ErrorMessage(e.getMessage(), ErrorMessages.RECORD_ALREADY_EXISTS.name(), "https://codingnuts.com");
        return Response.status(Response.Status.BAD_REQUEST).entity(errorMessage).build();
    }
}
