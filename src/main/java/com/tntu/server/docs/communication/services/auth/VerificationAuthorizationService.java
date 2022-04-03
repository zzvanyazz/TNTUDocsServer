package com.tntu.server.docs.communication.services.auth;

import com.tntu.server.docs.communication.models.auth.InvalidTokenException;
import com.tntu.server.docs.communication.models.auth.Principal;
import com.tntu.server.docs.core.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.AuthenticationFailedException;

@Service
public class VerificationAuthorizationService {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthorityService authorityService;

    public Principal authenticate(String jwtToken) throws InvalidTokenException, AuthenticationFailedException {
        var token = tokenService.parseAccessToken(jwtToken);
        var user = userService.findActiveUser(token.getUserId());

        tokenService.assertTokenValid(token);

        var authorities = authorityService.getAuthorities(user);
        var principal = Principal.fromUser(user);
        principal.setAuthorities(authorities);
        return principal;
    }
}
