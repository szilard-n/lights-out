package org.lightsout.shared.exception.mapper;

import io.netty.handler.codec.http.HttpResponseStatus;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;
import org.lightsout.shared.dto.ErrorResponse;
import org.lightsout.shared.exception.UnsolvableProblemException;

@Slf4j
@Provider
public class UnsolvableProblemExceptionMapper implements ExceptionMapper<UnsolvableProblemException> {

    @Override
    public Response toResponse(UnsolvableProblemException exception) {
        log.warn(exception.getMessage());
        int unprocessable = HttpResponseStatus.UNPROCESSABLE_ENTITY.code();
        ErrorResponse errorResponse = new ErrorResponse(unprocessable, "Problem could not be solved");

        return Response
                .status(unprocessable)
                .entity(errorResponse)
                .build();
    }
}
