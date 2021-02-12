package com.tntu.server.docs.communication.services.auth;

import com.tntu.server.docs.communication.models.auth.InvalidTokenException;
import com.tntu.server.docs.communication.models.auth.Token;
import com.tntu.server.docs.communication.models.responses.AuthResponseData;
import com.tntu.server.docs.communication.options.TokenOptions;
import com.tntu.server.docs.core.models.data.UserModel;
import com.tntu.server.docs.core.services.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.Period;
import java.time.ZoneOffset;
import java.util.Date;

@Service
public class TokenService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TokenOptions tokenOptions;


    public Token parseRefreshToken(String jwtToken) throws InvalidTokenException {
        try {
            return parseToken(jwtToken, tokenOptions.getRefreshTokenSigningKey());
        } catch (Exception ex) {
            log.error("Failed to parse refresh token.", ex);

            throw new InvalidTokenException();
        }
    }

    public Token parseAccessToken(String jwtToken) throws InvalidTokenException {
        try {
            return parseToken(jwtToken, tokenOptions.getAccessTokenSigningKey());
        } catch (Exception ex) {
            log.error("Failed to parse access token.", ex);

            throw new InvalidTokenException();
        }
    }

    private Token parseToken(String jwtToken, byte[] signingKey) {
        var claims = Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .setAllowedClockSkewSeconds(tokenOptions.getAllowedClockSkewSeconds())
                .requireIssuer(tokenOptions.getIssuer())
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();

        var token = new Token();
        token.setUserId(Long.parseLong(claims.getSubject()));
        token.setIssuedAt(claims.getIssuedAt().toInstant().atOffset(ZoneOffset.UTC));

        return token;
    }

    public boolean isTokenBlocked(UserModel user, Token token) {
        return user.getValidTokenTimestamp() != null &&
                token != null &&
                user.getValidTokenTimestamp()
                        .minusSeconds(tokenOptions.getAllowedClockSkewSeconds())
                        .isAfter(token.getIssuedAt());
    }

    public AuthResponseData createAuthData(UserModel user) {
        try {
            var accessTokenExpiration = calculateExpirationDate(tokenOptions.getAccessTokenExpirationPeriod());
            var accessToken = createAccessToken(user, accessTokenExpiration);
            var refreshTokenExpiration = calculateExpirationDate(tokenOptions.getRefreshTokenExpirationPeriod());
            var refreshToken = createRefreshToken(user, refreshTokenExpiration);
            var data = new AuthResponseData();

            data.setUserId(user.getId());
            data.setAccessToken(accessToken);
            data.setRefreshToken(refreshToken);
            data.setExpiration(accessTokenExpiration);

            return data;
        } catch (Exception ex) {
            log.error("Failed to create token.", ex);
            throw ex;
        }
    }

    private String createAccessToken(UserModel user, OffsetDateTime expiration) {
        return createToken(user, expiration, tokenOptions.getAccessTokenSigningKey());
    }

    private String createRefreshToken(UserModel user, OffsetDateTime expiration) {
        return createToken(user, expiration, tokenOptions.getRefreshTokenSigningKey());
    }

    private String createToken(UserModel user, OffsetDateTime expiration, byte[] bytesKey) {
        var key = Keys.hmacShaKeyFor(bytesKey);

        return Jwts.builder()
                .setSubject(String.valueOf(user.getId()))
                .setExpiration(Date.from(expiration.toInstant()))
                .setIssuer(tokenOptions.getIssuer())
                .setIssuedAt(new Date())
                .signWith(key)
                .compact();
    }

    private OffsetDateTime calculateExpirationDate(Period period) {
        return OffsetDateTime.now(ZoneOffset.UTC).plus(period);
    }

}
