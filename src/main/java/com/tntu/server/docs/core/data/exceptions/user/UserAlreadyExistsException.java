package com.tntu.server.docs.core.data.exceptions.user;

import com.tntu.server.docs.core.data.exceptions.DocsException;

public class UserAlreadyExistsException extends DocsException {
    public UserAlreadyExistsException(String normalisedUserId) {
        super(String.format("User with name '%s' already exists", normalisedUserId));
    }
}
