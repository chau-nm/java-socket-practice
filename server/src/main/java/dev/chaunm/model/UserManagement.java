package dev.chaunm.model;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

public class UserManagement {
    private final List<User> users;

    public UserManagement() {
        users = new CopyOnWriteArrayList<>();
    }

    public void add(User user) {
        users.add(user);
    }

    public Optional<User> getUserById(long id) {
        return users.stream().filter(u -> u.id() == id).findFirst();
    }
}
