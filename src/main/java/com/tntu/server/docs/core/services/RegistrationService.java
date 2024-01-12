package com.tntu.server.docs.core.services;

import com.tntu.server.docs.core.models.data.RegistrationModel;
import com.tntu.server.docs.core.models.data.RoleModel;
import com.tntu.server.docs.core.models.data.UserModel;
import com.tntu.server.docs.core.models.exceptions.ActionOnAdminRoleException;
import com.tntu.server.docs.core.models.exceptions.CanNotCreateUserException;
import com.tntu.server.docs.core.models.exceptions.CanNotSendMailException;
import com.tntu.server.docs.core.models.exceptions.RegistrationCodeNotFoundException;
import com.tntu.server.docs.core.models.exceptions.RegistrationProblemsException;
import com.tntu.server.docs.core.models.exceptions.RoleNotFoundException;
import com.tntu.server.docs.core.models.exceptions.UserAlreadyExistsException;
import com.tntu.server.docs.core.models.exceptions.UserAlreadyRegisteredException;
import com.tntu.server.docs.core.options.SecureOptions;
import com.tntu.server.docs.core.repositories.RegistrationRepository;
import com.tntu.server.docs.core.services.mail.MailService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.utility.RandomString;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final SecureOptions secureOptions;
    private final RegistrationRepository registrationRepository;
    private final SecureRandomService secureRandomService;
    private final MailService mailService;
    private final RoleService roleService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;


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
