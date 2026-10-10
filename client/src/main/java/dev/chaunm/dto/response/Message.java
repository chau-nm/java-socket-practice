package dev.chaunm.dto.response;

public record Message(User sender, String content, long timestamp) {

    public void render() {
        System.out.printf("%d - %s: %s ( %d )%n", sender.id(), sender.name(), content, timestamp);
    }

}
