package dev.chaunm.infrastructure;

public record Response<T>(ResponseStatus status, T data) {
}
