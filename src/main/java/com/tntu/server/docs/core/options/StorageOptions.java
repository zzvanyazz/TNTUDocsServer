package com.tntu.server.docs.core.options;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StorageOptions {

    @Value("${storage.location.root}")
    private String root;

    public String getRoot() {
        return root;
    }

}
