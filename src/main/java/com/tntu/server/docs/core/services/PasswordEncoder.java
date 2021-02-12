package com.tntu.server.docs.core.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public final class PasswordEncoder {
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public String encode(String text) {
        return encoder.encode(text);
    }

    public boolean matches(String clearText, String encodedText) {
        return encoder.matches(clearText, encodedText);
    }
}
