package com.tntu.server.docs.core.data.exceptions.auth;

public class LoginFailedException extends Exception {
    public LoginFailedException() {
        this("Username or password is incorrect.");
    }
    public LoginFailedException(String message) {
        super(message);
    }
}
