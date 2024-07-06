package org.lightsout.shared.exception.mapper;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;
import org.lightsout.shared.dto.ErrorResponse;

@Slf4j
@Provider
public class HttpExceptionMapper implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception exception) {
        log.error("Internal server error", exception);
        Response.Status internalServerError = Response.Status.INTERNAL_SERVER_ERROR;
        ErrorResponse errorResponse = new ErrorResponse(internalServerError.getStatusCode(), "Unexpected error. Contact support");

        return Response
                .status(internalServerError)
                .entity(errorResponse)
                .build();
    }
}
