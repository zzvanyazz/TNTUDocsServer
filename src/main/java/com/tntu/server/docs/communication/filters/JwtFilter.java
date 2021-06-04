package com.tntu.server.docs.communication.filters;

import com.tntu.server.docs.communication.models.auth.Principal;
import com.tntu.server.docs.communication.services.auth.VerificationAuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;

@Component
public final class JwtFilter extends GenericFilterBean {
    private final static String AUTHORIZATION_HEADER_NAME = "Authorization";
    private final static String BEARER_AUTHORIZATION_PREFIX = "Bearer ";


    @Autowired
    private VerificationAuthorizationService authService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        authenticate((HttpServletRequest) servletRequest);

        filterChain.doFilter(servletRequest, servletResponse);
    }


    private void authenticate(HttpServletRequest request) {
        extractToken(request)
                .flatMap(token -> authService.authenticate(token))
                .ifPresent(this::authenticate);
    }

    private void authenticate(Principal principal) {
        var auth = new UsernamePasswordAuthenticationToken(principal, "", principal.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }


    private Optional<String> extractToken(HttpServletRequest request) {
        var token = extractTokenFromHeaders(request).orElse("").trim();

        return token.length() > 0
                ? Optional.of(token)
                : Optional.empty();
    }

    private Optional<String> extractTokenFromHeaders(HttpServletRequest request) {
        var authHeader = request.getHeader(AUTHORIZATION_HEADER_NAME);

        if (authHeader != null && authHeader.startsWith(BEARER_AUTHORIZATION_PREFIX)) {
            String token = authHeader.substring(BEARER_AUTHORIZATION_PREFIX.length());

            return Optional.of(token);
        }

        return Optional.empty();
    }

}
