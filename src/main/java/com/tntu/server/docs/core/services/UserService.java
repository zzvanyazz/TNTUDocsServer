package com.tntu.server.docs.core.services;

import com.tntu.server.docs.core.data.exceptions.auth.CanNotCreateUserException;
import com.tntu.server.docs.core.data.exceptions.auth.LoginFailedException;
import com.tntu.server.docs.core.data.exceptions.user.UserAlreadyExistsException;
import com.tntu.server.docs.core.data.exceptions.user.UserIsBlockedException;
import com.tntu.server.docs.core.data.exceptions.user.UserNotFoundException;
import com.tntu.server.docs.core.data.models.user.UserModel;
import com.tntu.server.docs.core.repositories.UserModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.AuthenticationFailedException;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserModelRepository userModelRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public UserModel login(String username, String password) throws LoginFailedException, UserIsBlockedException {
        var user = userModelRepository
                .findLive(username.toLowerCase())
                .orElseThrow(LoginFailedException::new);

        if (!passwordEncoder.matches(password, user.getPasswordHash())) {
            throw new LoginFailedException();
        }

        if (!user.isEnabled()) {
            throw new UserIsBlockedException();
        }
        return user;
    }

    public UserModel findActiveUser(long userId) throws AuthenticationFailedException {
        return userModelRepository
                .findActive(userId)
                .orElseThrow(AuthenticationFailedException::new);
    }

    public UserModel findActiveUser(String username) throws UserNotFoundException {
        return userModelRepository
                .findActive(username)
                .orElseThrow(UserNotFoundException::new);
    }

    public List<UserModel> getUsers() {
        return userModelRepository.getAll();
    }


    public boolean existsByEmail(String email) {
        return userModelRepository.existsByEmail(email);
    }

    public UserModel getUser(long userId) throws UserNotFoundException {
        return userModelRepository
                .getUser(userId)
                .orElseThrow(UserNotFoundException::new);
    }

    public void ensureExists(long id) throws UserNotFoundException {
        if (!userModelRepository.existsById(id))
            throw new UserNotFoundException();
    }

    public UserModel createNewUser(UserModel newUserModel)
            throws UserAlreadyExistsException, CanNotCreateUserException {
        var normalisedUserName = newUserModel.getNormalizedUsername();
        if (userModelRepository.existsByName(normalisedUserName))
            throw new UserAlreadyExistsException(normalisedUserName);

        var user = userModelRepository.createUser(newUserModel);
        return user.orElseThrow(CanNotCreateUserException::new);
    }

}
