package com.tntu.server.docs.core.data.exceptions.auth;

public class CanNotCreateUserException extends Exception {
    public CanNotCreateUserException() {
        super("Can not create user");
    }
}
