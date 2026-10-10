package dev.chaunm.handler.join_room;

import dev.chaunm.model.Message;

import java.util.List;

public record JoinRoomResponseData(
        List<Message> messages
) {
}
