package dev.chaunm.view;

import dev.chaunm.dto.request.SendMessageRequestData;
import dev.chaunm.dto.response.Message;
import dev.chaunm.dto.response.SendMessageResponseData;
import dev.chaunm.protocol.Command;
import dev.chaunm.protocol.Request;
import dev.chaunm.protocol.Response;
import dev.chaunm.protocol.ResponseStatus;
import dev.chaunm.protocol.SocketClient;
import dev.chaunm.storage.AuthStorage;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Scanner;

public class ChatView {
    private enum Mode { WRITE, READ }

    private final SocketClient socketClient;
    private final AuthStorage authStorage;
    private final Scanner scanner;

    private final Object lock = new Object();
    private final Queue<Message> pending = new ArrayDeque<>();
    private Mode mode = Mode.WRITE;

    public ChatView(SocketClient socketClient, AuthStorage authStorage, Scanner scanner) {
        this.authStorage = authStorage;
        this.socketClient = socketClient;
        this.scanner = scanner;
    }

    public void render() throws IOException {
        startListener();

        while (true) {
            System.out.print("Please choose mode (Chat - w, Read - r, Quit - q): ");
            String mode = scanner.nextLine().trim();
            switch (mode) {
                case "w" -> handleSendMessage();
                case "r" -> handleReadMessage();
                case "q" -> {
                    return;
                }
                default -> System.out.println("Invalid mode");
            }
        }
    }

    private void handleSendMessage() throws IOException {
        System.out.println("-- WRITE mode -- (type /r to switch to READ mode)");
        while (true) {
            synchronized (lock) {
                if (!pending.isEmpty()) {
                    System.out.println("(" + pending.size() + " new message(s), type /r to read)");
                }
            }
            System.out.print("Enter: ");
            String message = scanner.nextLine();

            if (message.equals("/r")) {
                handleReadMessage();
                return;
            }
            if (message.isBlank()) {
                continue;
            }

            SendMessageRequestData sendMessageRequestData = new SendMessageRequestData(authStorage.getRoom(), message);
            Request request = new Request(authStorage.getId(), Command.SEND_MESSAGE, sendMessageRequestData);
            socketClient.sendRequest(request);
        }
    }

    private void handleReadMessage() {
        synchronized (lock) {
            mode = Mode.READ;
            System.out.println("-- READ mode -- (press Enter to go back)");
            while (!pending.isEmpty()) {
                pending.poll().render();
            }
        }

        scanner.nextLine();

        synchronized (lock) {
            mode = Mode.WRITE;
        }
    }

    private void startListener() {
        Thread listener = new Thread(() -> {
            try {
                while (true) {
                    Response<SendMessageResponseData> response = socketClient.waitForResponse(SendMessageResponseData.class);
                    if (response.status() != ResponseStatus.OK || response.data() == null) {
                        System.out.println("[!] Server rejected the message");
                        continue;
                    }

                    Message message = response.data().message();
                    synchronized (lock) {
                        if (mode == Mode.READ) {
                            message.render();
                        } else {
                            pending.add(message);
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("Disconnected from server: " + e.getMessage());
            }
        });
        listener.setDaemon(true);
        listener.start();
    }
}
