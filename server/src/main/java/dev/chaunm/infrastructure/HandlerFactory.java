package dev.chaunm.infrastructure;

import dev.chaunm.handler.login.LoginHandler;

public class HandlerFactory {
    public static Handler createHandler(Command command) {
        return switch (command) {
            case LOGIN -> new LoginHandler();
        };
    }
}
