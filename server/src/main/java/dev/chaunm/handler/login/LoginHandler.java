package dev.chaunm.handler.login;

import dev.chaunm.protocol.Handler;
import dev.chaunm.protocol.HandlerResult;
import dev.chaunm.protocol.Request;
import dev.chaunm.protocol.Response;
import dev.chaunm.protocol.ResponseStatus;
import dev.chaunm.exception.ServerException;
import dev.chaunm.exception.handler.InvalidRequestDataException;
import dev.chaunm.exception.handler.MissingClientIdException;
import dev.chaunm.util.JsonException;
import dev.chaunm.util.JsonUtil;

import java.util.List;

public class LoginHandler implements Handler {

    @Override
    public HandlerResult<LoginResponseData> handle(Request request) throws ServerException {
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
        Response<LoginResponseData> response = new Response<>(
                ResponseStatus.OK,
                new LoginResponseData(request.clientId(), name)
        );
        return new HandlerResult<>(List.of(request.clientId()), response);
    }
}
