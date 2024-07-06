package org.lightsout.shared.dto;

import lombok.Getter;

import java.sql.Timestamp;
import java.time.Instant;

@Getter
public class ErrorResponse {
    private final int status;
    private final Timestamp timestamp;
    private final String message;

    public ErrorResponse(int statusCode, String message) {
        this.status = statusCode;
        this.message = message;
        this.timestamp = Timestamp.from(Instant.now());
    }
}
