package dev.chaunm.protocol;

import dev.chaunm.exception.ServerException;
import dev.chaunm.log.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static final int DEFAULT_PORT = 5000;

    private final SocketManagement socketManagement;

    public Server() {
        this.socketManagement = new SocketManagement();
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(DEFAULT_PORT)) {
            Logger.info("Server started on port " + DEFAULT_PORT);

            while (true) {
                waitThenHandleRequest(serverSocket);
            }
        } catch (IOException e) {
            Logger.error("Error occurred while starting server: " + e.getMessage());
        }
    }

    private void waitThenHandleRequest(ServerSocket serverSocket) throws IOException {
        try {
            Socket socket = serverSocket.accept();
            Request request = socketManagement.getRequest(socket);

            Handler handler = HandlerFactory.createHandler(request.command());
            HandlerResult<?> result = handler.handle(request);
            socketManagement.sendResponses(result.responseReceivers(), result.response());
        } catch (ServerException e) {
            Logger.warn("Request handling failed: " + e.getMessage());
        }
    }
}
