package dev.chaunm.view;

import dev.chaunm.dto.request.LoginRequestData;
import dev.chaunm.protocol.Command;
import dev.chaunm.protocol.Request;
import dev.chaunm.protocol.Response;
import dev.chaunm.protocol.SocketClient;
import dev.chaunm.storage.AuthStorage;

import java.io.IOException;
import java.util.Scanner;

public class LoginView {

    private final SocketClient socketClient;
    private final AuthStorage authStorage;
    private final Scanner scanner;

    public LoginView(SocketClient socketClient, AuthStorage authStorage, Scanner scanner) {
        this.socketClient = socketClient;
        this.authStorage = authStorage;
        this.scanner = scanner;
    }

    public void render() throws IOException {
        System.out.print("Please enter your name:");
        String name = scanner.nextLine();
        socketClient.sendRequest(new Request(null, Command.LOGIN, new LoginRequestData(name)));
        Response<String> response = socketClient.waitForResponse(String.class);
        System.out.println(response.data());
    }

}
