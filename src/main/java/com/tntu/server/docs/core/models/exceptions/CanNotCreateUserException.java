package com.tntu.server.docs.core.models.exceptions;

public class CanNotCreateUserException extends Exception {
    public CanNotCreateUserException() {
        super("Can not create user");
    }
}
