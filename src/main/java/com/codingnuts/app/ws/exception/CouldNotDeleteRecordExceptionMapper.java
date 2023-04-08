package com.codingnuts.app.ws.exception;

import com.codingnuts.app.ws.ui.model.response.ErrorMessage;
import com.codingnuts.app.ws.ui.model.response.ErrorMessages;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class CouldNotDeleteRecordExceptionMapper implements ExceptionMapper<CouldNotDeleteRecordException> {
    @Override
    public Response toResponse(CouldNotDeleteRecordException exception) {
        ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(), ErrorMessages.COULD_NOT_DELETE_RECORD.name(),"https://codingnuts.com");
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorMessage).build();
    }
}
