package dev.chaunm;

import dev.chaunm.handler.HelloHandler;
import dev.chaunm.log.Logger;

import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static final int DEFAULT_PORT = 5000;

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(DEFAULT_PORT)) {
            Logger.info("Server started on port " + DEFAULT_PORT);

            while (true) {
                Socket socket = serverSocket.accept();
                Logger.info("New client connected: " + socket.getInetAddress().getHostAddress());

                HelloHandler handler = new HelloHandler(socket);
                handler.handle();
            }
        } catch (Exception e) {
            Logger.error("Error occurred while starting server: " + e.getMessage());
        }
    }

}
