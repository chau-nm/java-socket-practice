package dev.chaunm.protocol;

import dev.chaunm.exception.ServerException;

public interface Handler {
    HandlerResult<?> handle(Request request) throws ServerException;
}
