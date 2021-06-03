package com.tntu.server.docs.core.data.exceptions.user;

import com.tntu.server.docs.core.data.exceptions.DocsException;

public class UserIsBlockedException extends DocsException {

    public UserIsBlockedException() {
        super("This user is blocked.");
    }
}
