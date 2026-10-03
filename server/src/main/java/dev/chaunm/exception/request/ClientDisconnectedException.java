package dev.chaunm.exception.request;

import dev.chaunm.exception.ServerException;

public class ClientDisconnectedException extends ServerException {
    public ClientDisconnectedException() {
        super("Client disconnected before sending its request");
    }
}
