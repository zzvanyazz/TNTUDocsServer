package com.tntu.server.docs.communication.controllers;

import com.tntu.server.docs.communication.models.auth.AuthorityRole;
import com.tntu.server.docs.communication.models.mappings.RegistrationMapper;
import com.tntu.server.docs.communication.models.mappings.UserMapper;
import com.tntu.server.docs.communication.models.requests.users.StartUsersRegistration;
import com.tntu.server.docs.communication.models.responses.ResponseEntityFactory;
import com.tntu.server.docs.core.data.exceptions.auth.CanNotCreateUserException;
import com.tntu.server.docs.core.data.exceptions.auth.RegistrationCodeNotFoundException;
import com.tntu.server.docs.core.data.exceptions.auth.RegistrationProblemsException;
import com.tntu.server.docs.core.data.exceptions.user.ActionOnAdminRoleException;
import com.tntu.server.docs.core.data.exceptions.user.RoleNotFoundException;
import com.tntu.server.docs.core.data.models.user.RegistrationModel;
import com.tntu.server.docs.core.services.RegistrationService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/registration")
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @ApiOperation("Start user registration.")
    @PostMapping(value = "/start", produces = MediaType.APPLICATION_JSON_VALUE)
    @Secured({AuthorityRole.ADMIN})
    public ResponseEntity<?> startUsersRegistration(@RequestBody StartUsersRegistration registration)
            throws RoleNotFoundException, ActionOnAdminRoleException {
        var roleName = registration.getRoleName();
        var userEmails = registration.getUsersEmails();
        try {
            registrationService.startUserRegistration(roleName, userEmails);
            return ResponseEntityFactory.createOk();
        } catch (RegistrationProblemsException e) {
            var response = RegistrationMapper.toResponse(e);
            return ResponseEntity.status(207).body(response);
        }
    }

    @ApiOperation("Register.")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> register(@RequestBody RegistrationModel registration)
            throws RegistrationCodeNotFoundException, CanNotCreateUserException {
        var userModel = registrationService.register(registration);
        var dto = UserMapper.toDto(userModel);
        return ResponseEntityFactory.createCreated(dto);
    }
}
