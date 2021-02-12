package com.tntu.server.docs.core.options;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SecureOptions {

    @Value("${secure.registration.code.length}")
    private int registrationCodeLength;

    public int getRegistrationCodeLength() {
        return registrationCodeLength;
    }
}
