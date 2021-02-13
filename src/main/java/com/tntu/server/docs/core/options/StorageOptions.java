package com.tntu.server.docs.core.options;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StorageOptions {

    @Value("${storage.location.public}")
    private String publicLocation;
    @Value("${storage.location.private}")
    private String privateLocation;

    public String getPublicLocation() {
        return publicLocation;
    }

    public String getPrivateLocation() {
        return privateLocation;
    }
}
