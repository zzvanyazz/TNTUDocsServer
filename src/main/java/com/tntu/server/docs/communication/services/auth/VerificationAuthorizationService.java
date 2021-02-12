package com.tntu.server.docs.communication.services.auth;

import com.tntu.server.docs.communication.models.auth.Principal;
import com.tntu.server.docs.core.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VerificationAuthorizationService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthorityService authorityService;

    public Optional<Principal> authenticate(String jwtToken) {
        try {
            var token = tokenService.parseAccessToken(jwtToken);
            var user = userService.findActiveUser(token.getUserId());

            if (!tokenService.isTokenBlocked(user, token)) {
                var authorities = authorityService.getAuthorities(user);
                var principal = Principal.fromUser(user);

                principal.setAuthorities(authorities);

                return Optional.of(principal);
            }
        } catch (Exception e) {
            log.error("Authentication failed.", e);
        }

        return Optional.empty();
    }
}
