package dev.chaunm.exception.request;

import dev.chaunm.exception.ServerException;

public class EmptyRequestException extends ServerException {
    public EmptyRequestException() {
        super("Client sent an empty request");
    }
}
