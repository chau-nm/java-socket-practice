package dev.chaunm.infrastructure;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import dev.chaunm.util.JsonUtil;

public class Connection {
    private final PrintWriter writer;

    public Connection(Socket socket) throws IOException {
        this.writer = new PrintWriter(
                new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
    }

    public void sendResponse(Response<?> response) throws IOException {
        writer.println(JsonUtil.toJson(response));
        if (writer.checkError()) {
            throw new IOException("Failed to send response to client");
        }
    }
}
