package com.codingnuts.app.ws.exception;

import com.codingnuts.app.ws.ui.model.response.ErrorMessage;
import com.codingnuts.app.ws.ui.model.response.ErrorMessages;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable throwable) {
        ErrorMessage errorMessage = new ErrorMessage(throwable.getMessage(), ErrorMessages.INTERNAL_SERVER_ERROR.name(), "https://codingnuts.com");
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorMessage).build();
    }
}
