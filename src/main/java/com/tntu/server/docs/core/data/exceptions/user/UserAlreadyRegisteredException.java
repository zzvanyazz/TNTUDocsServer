package com.tntu.server.docs.core.data.exceptions.user;

public class UserAlreadyRegisteredException extends Exception {
    public UserAlreadyRegisteredException() {
        super("User already registered");
    }
}
