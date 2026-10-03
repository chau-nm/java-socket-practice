package dev.chaunm;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        String host = "localhost";
        int port = 5000;

        try (Client client = new Client()) {
            client.connect(host, port);
            client.sendLoginRequest("An");

            String response = client.waitForResponse();
            System.out.println("Received from server: " + response);
        }
    }
}
