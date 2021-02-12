package com.tntu.server.docs.core.models.exceptions;

public class UserIsBlockedException extends Exception {

    public UserIsBlockedException() {
        super("This user is blocked.");
    }
}
