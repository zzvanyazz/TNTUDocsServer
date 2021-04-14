package com.tntu.server.docs.core.data.exceptions.auth;

public class RegistrationCodeNotFoundException extends Exception {
    public RegistrationCodeNotFoundException() {
        super("Invalid registration code");
    }
}
