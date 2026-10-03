package dev.chaunm.exception.connection;

import dev.chaunm.exception.ServerException;

public class RequestReadException extends ServerException {
    public RequestReadException(Throwable cause) {
        super("Failed to read request from client", cause);
    }
}
