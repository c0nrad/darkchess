package com.c0nrad.darkchess.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class FormValidationException extends Exception implements
ExceptionMapper<FormValidationException> {
    
    static final long  serialVersionUID = 12;
    public FormValidationException(String message) {
        super(message);
    }

    @Override
    public Response toResponse(FormValidationException exception)
    {
        return Response.status(400).entity(exception.getMessage())
                                    .type("text/plain").build();
    }

    
}

