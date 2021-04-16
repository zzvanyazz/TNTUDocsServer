package com.tntu.server.docs.communication.services.auth;

import com.tntu.server.docs.communication.models.auth.AuthorityRole;
import com.tntu.server.docs.communication.models.auth.Principal;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public final class CurrentUserService {
    public long getId() {
        return getPrincipal().getId();
    }

    public boolean hasAuthority(String... authorities) {
        if (authorities == null) {
            return false;
        }

        return getPrincipal()
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(x -> Arrays.asList(authorities).contains(x));
    }

    public boolean isGranted() {
        return hasAuthority(AuthorityRole.ADMIN, AuthorityRole.MANAGER);
    }

    public Principal getPrincipal() {
        return (Principal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
