package com.tntu.server.docs.communication.services.auth;

import com.tntu.server.docs.communication.models.auth.AuthorityRole;
import com.tntu.server.docs.core.models.data.RoleModel;
import com.tntu.server.docs.core.models.data.UserModel;
import com.tntu.server.docs.core.models.exceptions.RoleNotFoundException;
import com.tntu.server.docs.core.models.exceptions.UserNotFoundException;
import com.tntu.server.docs.core.services.RoleService;
import com.tntu.server.docs.core.services.UserRolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthorityService {
    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRolesService userRolesService;

    private final Map<Long, String> authorityPerRoleId = new HashMap<>();


    @PostConstruct
    private void postConstruct() throws RoleNotFoundException {
        addMapping(AuthorityRole.ADMIN, RoleModel.ADMIN);
        addMapping(AuthorityRole.MANAGER, RoleModel.MANAGER);
    }

    @Transactional(readOnly = true)
    public List<String> getAuthorityNames(long userId) throws UserNotFoundException {
        return userRolesService.streamUserRoles(userId)
                .stream()
                .map(x -> findAuthority(x.getRoleId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<GrantedAuthority> getAuthorities(UserModel user) throws UserNotFoundException {
        return getAuthorityNames(user.getId())
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    public Optional<String> findAuthority(long roleId) {
        return Optional.ofNullable(authorityPerRoleId.get(roleId));
    }

    private void addMapping(String authority, String roleName) throws RoleNotFoundException {
        var id = roleService.findId(roleName);

        authorityPerRoleId.put(id, authority);
    }
}
