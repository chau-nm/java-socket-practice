package dev.chaunm.model;

public record Message(User sender, String content, long timestamp) {
}
