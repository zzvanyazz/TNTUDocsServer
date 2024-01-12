package com.tntu.server.docs.communication.services.auth;

import com.tntu.server.docs.communication.models.auth.InvalidTokenException;
import com.tntu.server.docs.communication.models.auth.TokenBlockedException;
import com.tntu.server.docs.communication.models.requests.auth.AuthRequest;
import com.tntu.server.docs.communication.models.requests.auth.RefreshTokenRequest;
import com.tntu.server.docs.communication.models.responses.AuthResponseData;
import com.tntu.server.docs.core.models.exceptions.LoginFailedException;
import com.tntu.server.docs.core.models.exceptions.UserIsBlockedException;
import com.tntu.server.docs.core.services.UserService;
import javax.mail.AuthenticationFailedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthorizeService {

    private final UserService userService;
    private final TokenService tokenService;

    public AuthResponseData authenticate(
            AuthRequest request) throws LoginFailedException, UserIsBlockedException {
        var user = userService.login(request.getUsername(), request.getPassword());

        return tokenService.createAuthData(user);
    }

    public AuthResponseData refreshToken(
            RefreshTokenRequest request
    ) throws InvalidTokenException, AuthenticationFailedException, TokenBlockedException {
        var token = tokenService.parseRefreshToken(request.getToken());
        var user = userService.findActiveUser(token.getUserId());

        if (tokenService.isTokenBlocked(user, token)) {
            throw new TokenBlockedException();
        }

        return tokenService.createAuthData(user);
    }

}



