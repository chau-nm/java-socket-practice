package dev.chaunm.dto.request;

public class LoginRequestData {
    private String name;

    public LoginRequestData(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
