package dev.chaunm;

import dev.chaunm.protocol.SocketClient;
import dev.chaunm.storage.AuthStorage;
import dev.chaunm.view.LoginView;
import dev.chaunm.view.RootView;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        AuthStorage authStorage = new AuthStorage();

        String socketHost = "localhost";
        int socketPort = 5000;
        SocketClient socketClient = new SocketClient();
        socketClient.connect(socketHost, socketPort);

        LoginView loginView = new LoginView(socketClient, authStorage, scanner);

        new RootView(
                loginView
        ).render();
    }
}
