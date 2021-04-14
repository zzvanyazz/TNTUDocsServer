package com.tntu.server.docs.core.data.exceptions.user;

public class UserIsBlockedException extends Exception {

    public UserIsBlockedException() {
        super("This user is blocked.");
    }
}
