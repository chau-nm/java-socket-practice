package dev.chaunm.view;

import dev.chaunm.dto.request.LoginRequestData;
import dev.chaunm.dto.response.LoginResponseData;
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
        Response<LoginResponseData> response = socketClient.waitForResponse(LoginResponseData.class);
        authStorage.save(response.data().id(), response.data().name());
        System.out.println("Login successful! Welcome, " + response.data().name() + " (" + response.data().id() + ") !");
    }

}
