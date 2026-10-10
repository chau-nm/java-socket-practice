package dev.chaunm.dto.response;

public record Message(User sender, String message, long timestamp) {

    public void render() {
        System.out.println(String.format("%l - %s: %s ( %s )", sender.id(), sender.name(), message, String.valueOf(timestamp)));
    }

}
