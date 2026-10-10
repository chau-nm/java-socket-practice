package dev.chaunm.view;

import java.io.IOException;

public class RootView {

    private final LoginView loginView;
    private final JoinRoomView joinRoomView;
    private final ChatView chatView;

    public RootView(LoginView loginView, JoinRoomView joinRoomView, ChatView chatView) {
        this.loginView = loginView;
        this.joinRoomView = joinRoomView;
        this.chatView = chatView;
    }

    public void render() throws IOException {
        System.out.println("Welcome to the application!");
        loginView.render();
        joinRoomView.render();
        chatView.render();
    }
}
