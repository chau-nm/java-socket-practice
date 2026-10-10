package dev.chaunm.protocol;

import dev.chaunm.model.Chat;
import dev.chaunm.exception.ServerException;
import dev.chaunm.log.Logger;
import dev.chaunm.model.UserManagement;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Server {
    private static final int DEFAULT_PORT = 5000;

    private final SocketManagement socketManagement;
    private final UserManagement userManagement;
    private final Chat chat;

    public Server(SocketManagement socketManagement, UserManagement userManagement, Chat chat) {
        this.socketManagement = socketManagement;
        this.userManagement = userManagement;
        this.chat = chat;
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(DEFAULT_PORT)) {
            Logger.info("Server started on port " + DEFAULT_PORT);

            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(() -> handleRequest(socket)).start();
            }
        } catch (IOException e) {
            Logger.error("Error occurred while starting server: " + e.getMessage());
        }
    }

    private void handleRequest(Socket socket) {
        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8)
            );

            String json;
            while ((json = reader.readLine()) != null) {
                Request request = socketManagement.getRequest(json, socket);

                Handler handler = HandlerFactory.createHandler(request.command(), chat, userManagement);
                HandlerResult<?> result = handler.handle(request);
                socketManagement.sendResponses(result.responseReceivers(), result.response());
            }
        } catch (ServerException e) {
            Logger.warn("Request handling failed: " + e.getMessage());
        } catch (IOException e) {
            Logger.error("Error occurred while starting server: " + e.getMessage());
        }
    }
}
