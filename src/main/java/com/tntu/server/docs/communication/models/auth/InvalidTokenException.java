package com.tntu.server.docs.communication.models.auth;

public class InvalidTokenException extends Exception {

    public InvalidTokenException(String message) {
        super(message);
    }

    public InvalidTokenException() {
    }

}
