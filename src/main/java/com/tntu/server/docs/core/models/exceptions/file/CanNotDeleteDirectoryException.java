package com.tntu.server.docs.core.models.exceptions.file;

public class CanNotDeleteDirectoryException extends Exception {
    public CanNotDeleteDirectoryException() {
        super("Can not delete directory");
    }
}
