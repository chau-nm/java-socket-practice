package dev.chaunm.protocol;

public record Response<T>(ResponseStatus status, T data) {
}
