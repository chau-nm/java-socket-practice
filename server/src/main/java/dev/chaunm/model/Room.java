package dev.chaunm.model;

import java.util.ArrayList;
import java.util.List;

public class Room {
    private final List<User> members;
    private final List<Message> messages;

    public Room() {
        this.members = new ArrayList<>();
        this.messages = new ArrayList<>();
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
}
