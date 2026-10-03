package dev.chaunm.infrastructure;

import dev.chaunm.util.JsonException;
import dev.chaunm.util.JsonUtil;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SocketManagement {

    private final Map<Long, Connection> connections;

    public SocketManagement() {
        this.connections = new HashMap<>();
    }

    public Request getRequest(Socket socket) throws IOException {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
        String json = reader.readLine();
        if (json == null) {
            throw new EOFException("Client disconnected before sending its request");
        }

        Request request;
        try {
            request = JsonUtil.fromJson(json, Request.class);
        } catch (JsonException e) {
            throw new IOException("Client sent an invalid request", e);
        }

        if (request == null) {
            throw new IOException("Client sent an empty request");
        }

        long clientId = request.clientId() == null
                ? IDGenerator.generateID()
                : request.clientId();
        connections.put(clientId, new Connection(socket));
        return request.withClientId(clientId);
    }

    public void removeSocket(long id) {
        connections.remove(id);
    }

    public void sendResponse(long clientId, Response<?> response) throws IOException {
        Connection connection = connections.get(clientId);
        if (connection == null) {
            throw new IOException("No connection found for client " + clientId);
        }
        connection.sendResponse(response);
    }

    public void sendResponses(List<Long> clientIds, Response<?> response) throws IOException {
        for (long clientId : clientIds) {
            sendResponse(clientId, response);
        }
    }
}
