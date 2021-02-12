package com.tntu.server.docs.core.services;

import com.tntu.server.docs.communication.models.auth.AuthorityRole;
import com.tntu.server.docs.core.models.data.RegistrationModel;
import com.tntu.server.docs.core.models.data.RoleModel;
import com.tntu.server.docs.core.models.data.UserModel;
import com.tntu.server.docs.core.models.exceptions.*;
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
        var exceptions = new ArrayList<Exception>();
        var role = roleService.getByName(roleName);
        if (role.getName().equals(RoleModel.ADMIN))
            throw new ActionOnAdminRoleException();
        for (String userEmail : userEmails) {
            try {
                startUserRegistration(role, userEmail);
            } catch (Exception e) {
                exceptions.add(e);
            }
        }
        if (!exceptions.isEmpty())
            throw new RegistrationProblemsException(exceptions);
    }

    public void startUserRegistration(RoleModel roleModel, String userEmail)
            throws CanNotSendMailException, UserAlreadyRegisteredException {
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
            throws UserAlreadyExistsException, CanNotCreateUserException, RegistrationCodeNotFoundException {
        var reg = registrationRepository
                .getRegistrationModelByCode(registrationModel.getCode())
                .orElseThrow(RegistrationCodeNotFoundException::new);
        UserModel userModel = new UserModel();
        userModel.setUsername(registrationModel.getUsername());
        userModel.setNormalizedUsername(registrationModel.getNormalizedUsername());
        userModel.setEmail(reg.getEmail());
        var passwordHash = passwordEncoder.encode(registrationModel.getPassword());
        userModel.setPasswordHash(passwordHash);
        registrationRepository.deleteByEmail(reg.getEmail());
        userModel = userService.createNewUser(userModel);
        return userModel;
    }


}
