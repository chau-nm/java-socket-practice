package dev.chaunm;

import java.net.Socket;

public class Client {

    private Socket socket;

    public void connect(String host, int port) throws Exception {
        socket = new Socket(host, port);
    }

    public void sendMessage(String message) throws Exception {
        if (socket != null && socket.isConnected()) {
            socket.getOutputStream().write((message + "\n").getBytes());
            socket.getOutputStream().flush();
        }
    }

    public void waitForResponse() throws Exception {
        if (socket != null && socket.isConnected()) {
            byte[] buffer = new byte[1024];
            int bytesRead = socket.getInputStream().read(buffer);
            String response = new String(buffer, 0, bytesRead);
            System.out.println("Received from server: " + response);
        }
    }
}
