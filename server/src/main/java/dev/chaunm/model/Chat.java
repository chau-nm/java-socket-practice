package dev.chaunm.model;

import java.util.Map;

public class Chat {

    private final Map<Long, Room> rooms;

    public Chat(Map<Long, Room> rooms) {
        this.rooms = rooms;
    }

    public Room getRoom(long roomId) {
        return rooms.get(roomId);
    }

    public boolean isValidRoomId(long roomId) {
        return rooms.containsKey(roomId);
    }
}
