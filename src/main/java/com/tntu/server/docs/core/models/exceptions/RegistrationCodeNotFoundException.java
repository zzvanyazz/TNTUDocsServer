package com.tntu.server.docs.core.models.exceptions;

public class RegistrationCodeNotFoundException extends Exception {
    public RegistrationCodeNotFoundException() {
        super("Invalid registration code");
    }
}
