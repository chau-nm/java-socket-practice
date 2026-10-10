package dev.chaunm.model;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

public class Room {
    private final List<User> members;
    private final List<Message> messages;

    public Room() {
        this.members = new CopyOnWriteArrayList<>();
        this.messages = new CopyOnWriteArrayList<>();
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void addMessage(Message message) {
        messages.add(message);
    }

    public void addMembers(User user) {
        members.add(user);
    }

    public List<Long> getMemberIds() {
        return members.stream().map(User::id).toList();
    }

    public Optional<User> getMember(long userId) {
        return members.stream().filter(u -> u.id() == userId).findFirst();
    }
}
