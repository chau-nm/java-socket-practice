package dev.chaunm.protocol;

import dev.chaunm.util.JsonUtil;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class SocketClient {
    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;

    public void connect(String host, int port) throws IOException {
        socket = new Socket(host, port);
        reader = new BufferedReader(
                new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
        writer = new BufferedWriter(
                new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
    }

    private void ensureConnected() throws IOException {
        if (socket == null || socket.isClosed() || !socket.isConnected()) {
            throw new IOException("Client is not connected");
        }
    }

    public void sendRequest(Request request) throws IOException {
        ensureConnected();
        String jsonRequest = JsonUtil.toJson(request);
        System.out.println("Sending request: " + jsonRequest);
        writer.write(jsonRequest);
        writer.newLine();
        writer.flush();
    }

    public <T> Response<T> waitForResponse(Class<T> dataType) throws IOException {
        ensureConnected();
        String jsonResponse = reader.readLine();
        if (jsonResponse == null) {
            throw new EOFException("Server closed the connection before sending a response");
        }
        return JsonUtil.fromJsonResponse(jsonResponse, dataType);
    }
}
