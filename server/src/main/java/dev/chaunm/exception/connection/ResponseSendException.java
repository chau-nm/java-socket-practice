package dev.chaunm.exception.connection;

import dev.chaunm.exception.ServerException;

public class ResponseSendException extends ServerException {
    public ResponseSendException(Throwable cause) {
        super("Failed to send response to client", cause);
    }
}
