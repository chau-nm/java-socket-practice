package dev.chaunm.handler;

import java.io.IOException;
import java.net.Socket;

public class HelloHandler extends Handler {

    public HelloHandler(Socket socket) throws IOException {
        super(socket);
    }

    @Override
    public void handle() throws IOException {
        String message = reader.readLine();
        System.out.println("Received: " + message);
        writer.println("Hello, " + message + "!");
    }
}
