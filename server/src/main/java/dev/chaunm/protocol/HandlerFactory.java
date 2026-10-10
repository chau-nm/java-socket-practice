package dev.chaunm.protocol;

import dev.chaunm.handler.join_room.JoinRoomHandler;
import dev.chaunm.handler.send_message.SendMessageHandler;
import dev.chaunm.model.Chat;
import dev.chaunm.handler.login.LoginHandler;
import dev.chaunm.model.UserManagement;

public class HandlerFactory {
    public static Handler createHandler(Command command, Chat chat, UserManagement userManagement) {
        return switch (command) {
            case LOGIN -> new LoginHandler(userManagement);
            case JOIN_ROOM -> new JoinRoomHandler(chat, userManagement);
            case SEND_MESSAGE -> new SendMessageHandler(chat);
        };
    }
}
