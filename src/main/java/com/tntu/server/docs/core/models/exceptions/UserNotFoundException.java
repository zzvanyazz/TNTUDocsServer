package com.tntu.server.docs.core.models.exceptions;

public class UserNotFoundException extends Exception {

    public UserNotFoundException() {
        super("Can not find a user.");
    }
}
