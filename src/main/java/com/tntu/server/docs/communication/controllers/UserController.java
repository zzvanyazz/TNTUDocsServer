package com.tntu.server.docs.communication.controllers;

import com.tntu.server.docs.communication.models.auth.AuthorityRole;
import com.tntu.server.docs.communication.models.mappings.RoleMapper;
import com.tntu.server.docs.communication.models.mappings.UserMapper;
import com.tntu.server.docs.communication.models.requests.AssignUserRoleRequest;
import com.tntu.server.docs.communication.models.responses.ResponseEntityFactory;
import com.tntu.server.docs.core.data.exceptions.DocsException;
import com.tntu.server.docs.core.data.exceptions.user.ActionOnAdminRoleException;
import com.tntu.server.docs.core.data.exceptions.user.RoleNotFoundException;
import com.tntu.server.docs.core.data.exceptions.user.UserNotFoundException;
import com.tntu.server.docs.core.services.UserRolesService;
import com.tntu.server.docs.core.services.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
@RequestMapping("v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRolesService userRolesService;


    @ApiOperation("Get user info.")
    @GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUser(@PathVariable long userId) throws DocsException {
        var userModel = userService.getUser(userId);
        var userDto = UserMapper.toDto(userModel);
        return ResponseEntity.ok(userDto);
    }

    @ApiOperation("Get users info.")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUsers() {
        var userModels = userService.getUsers();
        var userDto = userModels.stream().map(UserMapper::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(userDto);
    }

    @ApiOperation("Get user roles")
    @GetMapping(value = "/{userId}/roles", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getRoles(@PathVariable long userId) throws UserNotFoundException, RoleNotFoundException {
        var roles = userRolesService.getUserRoles(userId);
        var rolesDtos = roles.stream().map(RoleMapper::toDto).collect(Collectors.toList());
        return ResponseEntityFactory.createOk(rolesDtos);
    }

    @ApiOperation("Assign user role")
    @PostMapping(value = "/{userId}/assign", produces = MediaType.APPLICATION_JSON_VALUE)
    @Secured({AuthorityRole.ADMIN})
    public ResponseEntity<?> assignRole(
            @PathVariable long userId,
            @RequestBody @Valid AssignUserRoleRequest request)
            throws UserNotFoundException, RoleNotFoundException, ActionOnAdminRoleException {

        userRolesService.assignUserRole(userId, request.getRoleId());

        return ResponseEntityFactory.createOk();
    }

    @ApiOperation("Remove assign user role")
    @PostMapping(value = "/{userId}/remove-assign", produces = MediaType.APPLICATION_JSON_VALUE)
    @Secured({AuthorityRole.ADMIN})
    public ResponseEntity<?> removeAssignRole(
            @PathVariable long userId,
            @RequestBody @Valid AssignUserRoleRequest request)
            throws UserNotFoundException, RoleNotFoundException, ActionOnAdminRoleException {

        userRolesService.removeAssignUserRole(userId, request.getRoleId());
        return ResponseEntityFactory.createOk();
    }

}
