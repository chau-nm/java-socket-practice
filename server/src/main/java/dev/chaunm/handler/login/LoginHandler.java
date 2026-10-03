package dev.chaunm.handler.login;

import dev.chaunm.infrastructure.Handler;
import dev.chaunm.infrastructure.HandlerResult;
import dev.chaunm.infrastructure.Request;
import dev.chaunm.infrastructure.Response;
import dev.chaunm.infrastructure.ResponseStatus;
import dev.chaunm.exception.ServerException;
import dev.chaunm.exception.handler.InvalidRequestDataException;
import dev.chaunm.exception.handler.MissingClientIdException;
import dev.chaunm.util.JsonException;
import dev.chaunm.util.JsonUtil;

import java.util.List;

public class LoginHandler implements Handler {

    @Override
    public HandlerResult<String> handle(Request request) throws ServerException {
        if (request.clientId() == null) {
            throw new MissingClientIdException();
        }

        LoginRequestData data;
        try {
            data = request.data() == null
                    ? null
                    : JsonUtil.convert(request.data(), LoginRequestData.class);
        } catch (JsonException e) {
            throw new InvalidRequestDataException("Invalid login request data", e);
        }

        String name = data == null || data.name() == null || data.name().isBlank()
                ? "there"
                : data.name();
        Response<String> response = new Response<>(
                ResponseStatus.OK,
                "Hello, " + name + "!"
        );
        return new HandlerResult<>(List.of(request.clientId()), response);
    }
}
