package com.tntu.server.docs.core.data.exceptions.auth;

import com.tntu.server.docs.core.data.exceptions.DocsException;

public class RegistrationCodeNotFoundException extends DocsException {
    public RegistrationCodeNotFoundException() {
        super("Invalid registration code");
    }
}
