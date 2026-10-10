package dev.chaunm.storage;

public class AuthStorage {
    private long id;
    private long room;
    private String name;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getRoom() {
        return room;
    }

    public void save(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public void saveRoom(long room) {
        this.room = room;
    }
}
