package dev.chaunm.handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public abstract class Handler {

    protected final BufferedReader reader;
    protected final PrintWriter writer;

    protected Handler(Socket socket) throws IOException {
        this.reader = new BufferedReader(
                new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
        this.writer = new PrintWriter(socket.getOutputStream(), true);
    }

    public abstract void handle() throws IOException;
}
