package dev.chaunm.view;

import java.io.IOException;

public class RootView {

    private final LoginView loginView;

    public RootView(LoginView loginView) {
        this.loginView = loginView;
    }

    public void render() throws IOException {
        System.out.println("Welcome to the application!");
        loginView.render();
    }
}
