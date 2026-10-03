package dev.chaunm.exception.connection;

import dev.chaunm.exception.ServerException;

public class ConnectionNotFoundException extends ServerException {
    public ConnectionNotFoundException(long clientId) {
        super("No connection found for client " + clientId);
    }
}
