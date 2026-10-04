package dev.chaunm.storage;

public class AuthStorage {
    private long id;
    private String name;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void save(long id, String name) {
        this.id = id;
        this.name = name;
    }
}
