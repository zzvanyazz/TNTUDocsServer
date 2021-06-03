package com.tntu.server.docs.core.data.exceptions.user;

import com.tntu.server.docs.core.data.exceptions.DocsException;

public class UserAlreadyRegisteredException extends DocsException {
    public UserAlreadyRegisteredException() {
        super("User already registered");
    }
}
