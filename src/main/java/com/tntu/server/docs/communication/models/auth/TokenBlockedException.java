package com.tntu.server.docs.communication.models.auth;

import com.tntu.server.docs.core.data.exceptions.DocsException;

public class TokenBlockedException extends DocsException {

    public TokenBlockedException() {
        super("Token is blocked.");
    }

}
