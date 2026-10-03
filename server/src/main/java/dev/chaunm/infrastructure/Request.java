package dev.chaunm.infrastructure;

public record Request(Long clientId, Command command, Object data) {

    public Request withClientId(long clientId) {
        return new Request(clientId, command, data);
    }
}
