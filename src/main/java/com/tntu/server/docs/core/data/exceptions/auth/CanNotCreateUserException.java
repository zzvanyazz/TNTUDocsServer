package com.tntu.server.docs.core.data.exceptions.auth;

import com.tntu.server.docs.core.data.exceptions.DocsException;

public class CanNotCreateUserException extends DocsException {
    public CanNotCreateUserException() {
        super("Can not create user");
    }
}
