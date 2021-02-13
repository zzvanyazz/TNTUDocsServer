package com.tntu.server.docs.core.models.exceptions.file;

public class ResourceNotExistsException extends Exception {
    public ResourceNotExistsException() {
        super("Resource not exists");
    }
}
