package dev.chaunm.protocol;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import dev.chaunm.util.JsonUtil;
import dev.chaunm.exception.ServerException;

public class Connection {
    private final PrintWriter writer;

    public Connection(Socket socket) throws ServerException {
        try {
            this.writer = new PrintWriter(
                    new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
        } catch (IOException e) {
            throw new ServerException("Failed to register client connection", e);
        }
    }

    public void sendResponse(Response<?> response) throws ServerException {
        writer.println(JsonUtil.toJson(response));
        if (writer.checkError()) {
            throw new ServerException("Failed to send response to client", new IOException("Output stream reported an error"));
        }
    }
}
