package dev.chaunm.exception.connection;

import dev.chaunm.exception.ServerException;

public class ConnectionRegistrationException extends ServerException {
    public ConnectionRegistrationException(Throwable cause) {
        super("Failed to register client connection", cause);
    }
}
