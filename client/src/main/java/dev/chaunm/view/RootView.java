package dev.chaunm.view;

import java.io.IOException;

public class RootView {

    private final LoginView loginView;
    private final JoinRoomView joinRoomView;

    public RootView(LoginView loginView, JoinRoomView joinRoomView) {
        this.loginView = loginView;
        this.joinRoomView = joinRoomView;
    }

    public void render() throws IOException {
        System.out.println("Welcome to the application!");
        loginView.render();
        joinRoomView.render();
    }
}
