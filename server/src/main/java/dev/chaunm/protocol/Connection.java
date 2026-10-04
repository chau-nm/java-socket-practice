package dev.chaunm.protocol;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import dev.chaunm.util.JsonUtil;
import dev.chaunm.exception.ServerException;
import dev.chaunm.exception.connection.ConnectionRegistrationException;
import dev.chaunm.exception.connection.ResponseSendException;

public class Connection {
    private final PrintWriter writer;

    public Connection(Socket socket) throws ServerException {
        try {
            this.writer = new PrintWriter(
                    new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
        } catch (IOException e) {
            throw new ConnectionRegistrationException(e);
        }
    }

    public void sendResponse(Response<?> response) throws ServerException {
        writer.println(JsonUtil.toJson(response));
        if (writer.checkError()) {
            throw new ResponseSendException(new IOException("Output stream reported an error"));
        }
    }
}
