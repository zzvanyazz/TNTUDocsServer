package com.tntu.server.docs.core.data.exceptions.auth;

import com.tntu.server.docs.core.data.exceptions.DocsException;

public class LoginFailedException extends DocsException {

    public LoginFailedException() {
        super("Username or password is incorrect.");
    }

}
