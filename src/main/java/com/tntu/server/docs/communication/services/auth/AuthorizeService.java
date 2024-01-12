package com.tntu.server.docs.communication.services.auth;

import com.tntu.server.docs.communication.models.auth.InvalidTokenException;
import com.tntu.server.docs.communication.models.auth.TokenBlockedException;
import com.tntu.server.docs.communication.models.requests.auth.AuthRequest;
import com.tntu.server.docs.communication.models.requests.auth.RefreshTokenRequest;
import com.tntu.server.docs.communication.models.responses.AuthResponseData;
import com.tntu.server.docs.core.data.exceptions.auth.LoginFailedException;
import com.tntu.server.docs.core.services.UserService;
import javax.mail.AuthenticationFailedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthorizeService {

    private final UserService userService;
    private final TokenService tokenService;

    public AuthResponseData authenticate(AuthRequest request) throws LoginFailedException {
        var email = request.getEmail();
        var password = request.getPassword();

        var user = userService.login(email, password);

        return tokenService.createAuthData(user);
    }

    public AuthResponseData refreshToken(
            RefreshTokenRequest request
    ) throws InvalidTokenException, AuthenticationFailedException, TokenBlockedException {
        var token = tokenService.parseRefreshToken(request.getToken());
        var user = userService.findActiveUser(token.getUserId());
        tokenService.assertTokenValid(token);
        return tokenService.createAuthData(user);
    }

}



