package dev.chaunm.handler.send_message;

import dev.chaunm.exception.ServerException;
import dev.chaunm.model.Chat;
import dev.chaunm.model.Message;
import dev.chaunm.model.Room;
import dev.chaunm.model.User;
import dev.chaunm.protocol.*;
import dev.chaunm.util.JsonUtil;

public class SendMessageHandler implements Handler {

    private final Chat chat;

    public SendMessageHandler(Chat chat) {
        this.chat = chat;
    }

    @Override
    public HandlerResult<SendMessageResponseData> handle(Request request) throws ServerException {
        if (request.clientId() == null) {
            throw new ServerException("Request clientId is required");
        }

        SendMessageRequestData data = request.data() == null ? null
                : JsonUtil.convert(request.data(), SendMessageRequestData.class);

        if (data == null) {
            throw new ServerException("Request data is missing");
        }

        long roomId = data.roomId();
        String message = data.message();

        Room room = chat.getRoom(roomId);

        if (room == null) {
            throw new ServerException("Room not found " + roomId);
        }

        User sender = room.getMember(request.clientId())
                .orElseThrow(() -> new ServerException(String.format("Not found user %s at room %s", request.clientId(), roomId)));

        Message messageStored = new Message(sender, message, System.currentTimeMillis());
        room.addMessage(messageStored);

        Response<SendMessageResponseData> response = new Response<>(
                ResponseStatus.OK,
                new SendMessageResponseData(messageStored)
        );
        return new HandlerResult<>(room.getMemberIds(), response);
    }
}
