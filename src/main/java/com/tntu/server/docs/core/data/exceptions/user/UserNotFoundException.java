package com.tntu.server.docs.core.data.exceptions.user;

public class UserNotFoundException extends Exception {

    public UserNotFoundException() {
        super("Can not find a user.");
    }
}
