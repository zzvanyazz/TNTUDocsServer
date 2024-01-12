package com.tntu.server.docs.communication.services.auth;

import com.tntu.server.docs.communication.models.auth.InvalidTokenException;
import com.tntu.server.docs.communication.models.auth.Principal;
import com.tntu.server.docs.core.services.UserService;
import javax.mail.AuthenticationFailedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VerificationAuthorizationService {

    private final UserService userService;
    private final TokenService tokenService;
    private final AuthorityService authorityService;

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
