package com.codingnuts.app.ws.exception;

import com.codingnuts.app.ws.ui.model.response.ErrorMessage;
import com.codingnuts.app.ws.ui.model.response.ErrorMessages;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class MissingRequiredFieldExceptionMapper implements ExceptionMapper<MissingRequiredFieldException> {
    @Override
    public Response toResponse(MissingRequiredFieldException e) {
        ErrorMessage errorMessage = new ErrorMessage(e.getMessage(), ErrorMessages.MISSING_REQUIRED_FIELD.name(),"https://codingnuts.com");
        return Response.status(Response.Status.BAD_REQUEST).entity(errorMessage).build();
    }
}
