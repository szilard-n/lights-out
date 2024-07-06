package org.lightsout.shared.exception.mapper;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;
import org.lightsout.shared.dto.ErrorResponse;
import org.lightsout.shared.exception.ResourceNotFoundException;

@Slf4j
@Provider
public class ResourceNotFoundExceptionMapper implements ExceptionMapper<ResourceNotFoundException> {

    @Override
    public Response toResponse(ResourceNotFoundException exception) {
        log.debug("Resource not found. {}", exception.getMessage());
        Response.Status notFound = Response.Status.NOT_FOUND;
        ErrorResponse errorResponse = new ErrorResponse(notFound.getStatusCode(), exception.getMessage());

        return Response
                .status(notFound)
                .entity(errorResponse)
                .build();
    }
}
