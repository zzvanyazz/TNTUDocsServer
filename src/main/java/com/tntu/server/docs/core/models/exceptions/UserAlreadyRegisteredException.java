package com.tntu.server.docs.core.models.exceptions;

public class UserAlreadyRegisteredException extends Exception {
    public UserAlreadyRegisteredException() {
        super("User already registered");
    }
}
