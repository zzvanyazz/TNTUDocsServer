package com.tntu.server.docs.core.services;

import com.tntu.server.docs.core.data.exceptions.DocsException;
import com.tntu.server.docs.core.data.exceptions.auth.CanNotCreateUserException;
import com.tntu.server.docs.core.data.exceptions.auth.RegistrationCodeNotFoundException;
import com.tntu.server.docs.core.data.exceptions.auth.RegistrationProblemsException;
import com.tntu.server.docs.core.data.exceptions.user.ActionOnAdminRoleException;
import com.tntu.server.docs.core.data.exceptions.user.RoleNotFoundException;
import com.tntu.server.docs.core.data.exceptions.user.UserAlreadyRegisteredException;
import com.tntu.server.docs.core.data.models.user.RegistrationModel;
import com.tntu.server.docs.core.data.models.user.RoleModel;
import com.tntu.server.docs.core.data.models.user.UserModel;
import com.tntu.server.docs.core.options.SecureOptions;
import com.tntu.server.docs.core.repositories.RegistrationRepository;
import com.tntu.server.docs.core.services.mail.MailService;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RegistrationService {

    @Autowired
    private SecureOptions secureOptions;

    @Autowired
    private RegistrationRepository registrationRepository;

    @Autowired
    private SecureRandomService secureRandomService;

    @Autowired
    private MailService mailService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public void startUserRegistration(String roleName, List<String> userEmails)
            throws RoleNotFoundException, ActionOnAdminRoleException, RegistrationProblemsException {
        var exceptions = new ArrayList<DocsException>();
        var role = roleService.getByName(roleName);
        if (role.getName().equals(RoleModel.ADMIN))
            throw new ActionOnAdminRoleException();
        for (String userEmail : userEmails) {
            try {
                startUserRegistration(role, userEmail);
            } catch (DocsException e) {
                exceptions.add(e);
            }
        }
        if (!exceptions.isEmpty())
            throw new RegistrationProblemsException(exceptions);
    }

    public void startUserRegistration(RoleModel roleModel, String userEmail) throws DocsException {
        if (userService.existsByEmail(userEmail)) {
            throw new UserAlreadyRegisteredException();
        }
        if (registrationRepository.existsByEmail(userEmail)) {
            registrationRepository.deleteByEmail(userEmail);
        }
        var registrationCode = generateRegistrationCode();
        mailService.sendRegistrationMessage(registrationCode, userEmail, roleModel);
        var roleId = roleModel.getId();
        registrationRepository.save(userEmail, registrationCode, roleId);
    }

    private String generateRegistrationCode() {
        var length = secureOptions.getRegistrationCodeLength();
        try {
            return secureRandomService.generateAlphaNumeric(length);
        } catch (Exception e) {
            return RandomString.make(length);
        }
    }

    public UserModel register(RegistrationModel registrationModel)
            throws CanNotCreateUserException, RegistrationCodeNotFoundException {
        var reg = registrationRepository
                .getRegistrationModelByCode(registrationModel.getCode())
                .orElseThrow(RegistrationCodeNotFoundException::new);
        UserModel userModel = new UserModel();
        userModel.setUsername(registrationModel.getUsername());
        userModel.setEmail(reg.getEmail());
        var passwordHash = passwordEncoder.encode(registrationModel.getPassword());
        userModel.setPasswordHash(passwordHash);
        userModel = userService.createNewUser(userModel);
        registrationRepository.deleteByEmail(reg.getEmail());
        return userModel;
    }


}
