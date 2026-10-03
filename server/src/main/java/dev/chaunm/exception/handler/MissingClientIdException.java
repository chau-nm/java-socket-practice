package dev.chaunm.exception.handler;

import dev.chaunm.exception.ServerException;

public class MissingClientIdException extends ServerException {
    public MissingClientIdException() {
        super("Request clientId is required");
    }
}
