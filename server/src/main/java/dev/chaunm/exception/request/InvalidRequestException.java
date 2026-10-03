package dev.chaunm.exception.request;

import dev.chaunm.exception.ServerException;

public class InvalidRequestException extends ServerException {
    public InvalidRequestException(Throwable cause) {
        super("Client sent an invalid request", cause);
    }
}
