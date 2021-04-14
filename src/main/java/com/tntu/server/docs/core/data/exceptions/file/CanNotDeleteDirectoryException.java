package com.tntu.server.docs.core.data.exceptions.file;

public class CanNotDeleteDirectoryException extends Exception {
    public CanNotDeleteDirectoryException() {
        super("Can not delete directory");
    }
}
