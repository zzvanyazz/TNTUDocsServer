package com.tntu.server.docs.core.data.exceptions.user;

public class UserAlreadyExistsException extends Exception {
    public UserAlreadyExistsException(String normalisedUserId) {
        super(String.format("User with name '%s' already exists", normalisedUserId));
    }
}
