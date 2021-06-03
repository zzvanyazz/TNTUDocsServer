package com.tntu.server.docs.core.data.exceptions.user;

import com.tntu.server.docs.core.data.exceptions.DocsException;

public class UserNotFoundException extends DocsException {

    public UserNotFoundException() {
        super("Can not find a user.");
    }
}
