package com.tntu.server.docs.core.services;

import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public final class SecureRandomService {
    private static final String ALGORITHM = "SHA1PRNG";


    public String generateDigits(int length) throws NoSuchAlgorithmException {
        var random = SecureRandom.getInstance(ALGORITHM);
        var bound = (int) Math.pow(10, length) - 1;
        var code = random.nextInt(bound);

        return String.format("%0" + length + "d", code);
    }

    public String generateAlphaNumeric(int length) throws NoSuchAlgorithmException {
        var random = SecureRandom.getInstance(ALGORITHM);
        var bound = (int) 'z' + 1;

        return Stream
                .generate(() -> (char) random.nextInt(bound))
                .filter(x -> (x >= '0' && x <= '9') || x >= 'a' || (x >= 'A' && x <= 'Z'))
                .limit(length)
                .map(String::valueOf)
                .collect(Collectors.joining());
    }
}
