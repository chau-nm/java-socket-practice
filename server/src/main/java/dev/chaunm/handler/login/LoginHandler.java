package dev.chaunm.handler.login;

import dev.chaunm.model.User;
import dev.chaunm.model.UserManagement;
import dev.chaunm.protocol.Handler;
import dev.chaunm.protocol.HandlerResult;
import dev.chaunm.protocol.Request;
import dev.chaunm.protocol.Response;
import dev.chaunm.protocol.ResponseStatus;
import dev.chaunm.exception.ServerException;
import dev.chaunm.util.JsonException;
import dev.chaunm.util.JsonUtil;

import java.util.List;

public class LoginHandler implements Handler {

    private final UserManagement userManagement;

    public LoginHandler(UserManagement userManagement) {
        this.userManagement = userManagement;
    }

    @Override
    public HandlerResult<LoginResponseData> handle(Request request) throws ServerException {
        if (request.clientId() == null) {
            throw new ServerException("Request clientId is required");
        }

        LoginRequestData data;
        try {
            data = request.data() == null
                    ? null
                    : JsonUtil.convert(request.data(), LoginRequestData.class);
        } catch (JsonException e) {
            throw new ServerException("Invalid login request data", e);
        }

        String name = data == null || data.name() == null || data.name().isBlank()
                ? "there"
                : data.name();

        userManagement.add(new User(request.clientId(), name));

        Response<LoginResponseData> response = new Response<>(
                ResponseStatus.OK,
                new LoginResponseData(request.clientId(), name)
        );
        return new HandlerResult<>(List.of(request.clientId()), response);
    }
}
