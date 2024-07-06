package org.lightsout.shared.exception;

import java.util.UUID;

public class UnsolvableProblemException extends RuntimeException {
    public UnsolvableProblemException(UUID problemId) {
        super(String.format("The with problem ID %s can not be solved", problemId));
    }
}
