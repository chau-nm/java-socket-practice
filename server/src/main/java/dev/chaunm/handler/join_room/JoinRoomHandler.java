package dev.chaunm.handler.join_room;

import dev.chaunm.model.Chat;
import dev.chaunm.exception.ServerException;
import dev.chaunm.model.Room;
import dev.chaunm.model.User;
import dev.chaunm.model.UserManagement;
import dev.chaunm.protocol.*;
import dev.chaunm.util.JsonUtil;

import java.util.List;

public class JoinRoomHandler implements Handler {

    private final Chat chat;
    private final UserManagement userManagement;

    public JoinRoomHandler(Chat chat, UserManagement userManagement) {
        this.chat = chat;
        this.userManagement = userManagement;
    }

    @Override
    public HandlerResult<JoinRoomResponseData> handle(Request request) throws ServerException {
        if (request.clientId() == null) {
            throw new ServerException("Request clientId is required");
        }

        JoinRoomRequestData data = request.data() == null
                ? null
                : JsonUtil.convert(request.data(), JoinRoomRequestData.class);

        if (data == null || !chat.isValidRoomId(data.roomId())) {
            throw new ServerException("Invalid roomId: " + (data == null ? "null" : data.roomId()));
        }

        Room room = chat.getRoom(data.roomId());
        User user = userManagement.getUserById(request.clientId())
                .orElseThrow(() -> new ServerException("User not found for client " + request.clientId()));

        room.addMembers(user);

        Response<JoinRoomResponseData> response = new Response<>(
                ResponseStatus.OK,
                new JoinRoomResponseData(room.getMessages())
        );
        return new HandlerResult<>(List.of(request.clientId()), response);
    }
}
