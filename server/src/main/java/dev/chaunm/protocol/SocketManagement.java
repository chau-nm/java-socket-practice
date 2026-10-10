package dev.chaunm.protocol;

import dev.chaunm.exception.ServerException;
import dev.chaunm.log.Logger;
import dev.chaunm.util.JsonException;
import dev.chaunm.util.JsonUtil;

import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SocketManagement {

    private final Map<Long, Connection> connections;

    public SocketManagement() {
        this.connections = new ConcurrentHashMap<>();
    }

    public Request getRequest(String json, Socket socket) throws ServerException {
        Request request;
        try {
            request = JsonUtil.fromJson(json, Request.class);
        } catch (JsonException e) {
            throw new ServerException("Client sent an invalid request", e);
        }

        if (request == null) {
            throw new ServerException("Client sent an empty request");
        }

        long clientId = request.clientId() == null
                ? IDGenerator.generateID()
                : request.clientId();
        Logger.info("Received request: " + request.command() + " from clientId: " + clientId);
        connections.put(clientId, new Connection(socket));
        return request.withClientId(clientId);
    }

    public void removeSocket(long id) {
        connections.remove(id);
    }

    public void sendResponse(long clientId, Response<?> response) throws ServerException {
        Connection connection = connections.get(clientId);
        if (connection == null) {
            throw new ServerException("No connection found for client " + clientId);
        }
        connection.sendResponse(response);
    }

    public void sendResponses(List<Long> clientIds, Response<?> response) throws ServerException {
        for (long clientId : clientIds) {
            // Keep going if one receiver fails, so the others still get the message
            try {
                sendResponse(clientId, response);
            } catch (ServerException e) {
                Logger.warn("Failed to send response to client " + clientId + ": " + e.getMessage());
            }
        }
    }
}
