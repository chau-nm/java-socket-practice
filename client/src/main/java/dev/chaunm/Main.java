package dev.chaunm;

public class Main {
    public static void main(String[] args) throws Exception {
        String host = "localhost";
        int port = 5000;

        Client client = new Client();
        client.connect(host, port);
        client.sendMessage("Hello, Server!");

        client.waitForResponse();
    }
}
