package dev.chaunm.view;

import dev.chaunm.dto.request.JoinRoomRequestData;
import dev.chaunm.dto.response.JoinRoomResponseData;
import dev.chaunm.dto.response.Message;
import dev.chaunm.protocol.Command;
import dev.chaunm.protocol.Request;
import dev.chaunm.protocol.Response;
import dev.chaunm.protocol.SocketClient;
import dev.chaunm.storage.AuthStorage;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class JoinRoomView {
    private final SocketClient socketClient;
    private final AuthStorage authStorage;
    private final Scanner scanner;

    public JoinRoomView(SocketClient socketClient, AuthStorage authStorage, Scanner scanner) {
        this.authStorage = authStorage;
        this.socketClient = socketClient;
        this.scanner = scanner;
    }

    public void render() throws IOException {
        System.out.println("There are 5 room: 1, 2, 3, 4, 5");
        System.out.print("Please choose one room: ");
        long roomId = scanner.nextLong();
        System.out.println(roomId);

        socketClient.sendRequest(new Request(authStorage.getId(), Command.JOIN_ROOM, new JoinRoomRequestData(roomId)));
        Response<JoinRoomResponseData> response = socketClient.waitForResponse(JoinRoomResponseData.class);

        List<Message> messages = response.data().messages();
        System.out.println("Welcome to join room: " + roomId);
        messages.forEach(Message::render);
    }
}
