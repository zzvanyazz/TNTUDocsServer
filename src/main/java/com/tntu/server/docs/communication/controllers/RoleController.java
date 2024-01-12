package com.tntu.server.docs.communication.controllers;


import com.tntu.server.docs.communication.models.auth.AuthorityRole;
import com.tntu.server.docs.communication.models.mappings.RoleMapper;
import com.tntu.server.docs.core.services.RoleService;
import io.swagger.annotations.ApiOperation;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @ApiOperation("List roles.")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Secured({AuthorityRole.ADMIN, AuthorityRole.MANAGER})
    public ResponseEntity<?> listRoles() {
        var roles = roleService.getAll();
        var roleDtos = roles.stream().map(RoleMapper::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(roleDtos);
    }

}
