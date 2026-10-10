package dev.chaunm;

import dev.chaunm.model.Chat;
import dev.chaunm.model.Room;
import dev.chaunm.model.UserManagement;
import dev.chaunm.protocol.Server;
import dev.chaunm.protocol.SocketManagement;

import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Map<Long, Room> rooms = Map.of(
                1L, new Room(),
                2L, new Room(),
                3L, new Room(),
                4L, new Room(),
                5L, new Room()
        );
        Chat chat = new Chat(rooms);
        SocketManagement socketManagement = new SocketManagement();
        UserManagement userManagement = new UserManagement();

        new Server(socketManagement, userManagement, chat).start();
    }
}
