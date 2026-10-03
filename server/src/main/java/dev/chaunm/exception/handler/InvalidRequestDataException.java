package dev.chaunm.exception.handler;

import dev.chaunm.exception.ServerException;

public class InvalidRequestDataException extends ServerException {
    public InvalidRequestDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
