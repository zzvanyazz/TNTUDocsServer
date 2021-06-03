package com.tntu.server.docs.core.data.exceptions.storage.resource;

public class ResourceNotExistsException extends ResourceException {
    public ResourceNotExistsException() {
        super("Resource not exists");
    }
}
