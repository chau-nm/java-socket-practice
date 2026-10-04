package dev.chaunm.protocol;

import dev.chaunm.exception.ServerException;
import dev.chaunm.exception.connection.ConnectionNotFoundException;
import dev.chaunm.exception.connection.RequestReadException;
import dev.chaunm.exception.request.ClientDisconnectedException;
import dev.chaunm.exception.request.EmptyRequestException;
import dev.chaunm.exception.request.InvalidRequestException;
import dev.chaunm.log.Logger;
import dev.chaunm.util.JsonException;
import dev.chaunm.util.JsonUtil;

import java.io.BufferedReader;
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

    public Request getRequest(Socket socket) throws ServerException {
        String json;
        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            json = reader.readLine();
        } catch (IOException e) {
            throw new RequestReadException(e);
        }
        if (json == null) {
            throw new ClientDisconnectedException();
        }

        Request request;
        try {
            request = JsonUtil.fromJson(json, Request.class);
        } catch (JsonException e) {
            throw new InvalidRequestException(e);
        }

        if (request == null) {
            throw new EmptyRequestException();
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
            throw new ConnectionNotFoundException(clientId);
        }
        connection.sendResponse(response);
    }

    public void sendResponses(List<Long> clientIds, Response<?> response) throws ServerException {
        for (long clientId : clientIds) {
            sendResponse(clientId, response);
        }
    }
}
