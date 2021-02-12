package com.tntu.server.docs.communication.options;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.Period;

@Service
public final class TokenOptions {
    @Value("${jwt.token.issuer}")
    private String issuer;

    @Value("${jwt.token.allowedClockSkewSeconds}")
    private int allowedClockSkewSeconds;

    @Value("${jwt.token.access.signingKey}")
    private String accessSigningKey;

    @Value("${jwt.token.access.expirationInDays}")
    private int accessExpirationInDays;

    @Value("${jwt.token.refresh.signingKey}")
    private String refreshSigningKey;

    @Value("${jwt.token.refresh.expirationInDays}")
    private int refreshExpirationInDays;


    public String getIssuer() {
        return issuer;
    }

    public int getAllowedClockSkewSeconds() {
        return allowedClockSkewSeconds;
    }

    public byte[] getAccessTokenSigningKey() {
        return accessSigningKey.getBytes(StandardCharsets.UTF_8);
    }

    public Period getAccessTokenExpirationPeriod() {
        return Period.ofDays(accessExpirationInDays);
    }

    public byte[] getRefreshTokenSigningKey() {
        return refreshSigningKey.getBytes(StandardCharsets.UTF_8);
    }

    public Period getRefreshTokenExpirationPeriod() {
        return Period.ofDays(refreshExpirationInDays);
    }
}
