package dev.chaunm.infrastructure;

import dev.chaunm.exception.ServerException;

public interface Handler {
    HandlerResult<?> handle(Request request) throws ServerException;
}
