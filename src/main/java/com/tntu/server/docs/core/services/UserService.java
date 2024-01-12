package com.tntu.server.docs.core.services;

import com.tntu.server.docs.core.data.exceptions.auth.CanNotCreateUserException;
import com.tntu.server.docs.core.data.exceptions.auth.LoginFailedException;
import com.tntu.server.docs.core.data.exceptions.user.UserNotFoundException;
import com.tntu.server.docs.core.data.models.user.UserModel;
import com.tntu.server.docs.core.repositories.UserModelRepository;
import java.util.List;
import javax.mail.AuthenticationFailedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserModelRepository userModelRepository;
    private final PasswordEncoder passwordEncoder;


    @Transactional(readOnly = true)
    public UserModel login(String email, String password) throws LoginFailedException {
        var user = userModelRepository
                .findLive(email)
                .orElseThrow(LoginFailedException::new);

        if (!passwordEncoder.matches(password, user.getPasswordHash())) {
            throw new LoginFailedException();
        }
        return user;
    }

    @Transactional(readOnly = true)
    public UserModel findActiveUser(long userId) throws AuthenticationFailedException {
        return userModelRepository
                .findActive(userId)
                .orElseThrow(AuthenticationFailedException::new);
    }

    @Transactional(readOnly = true)
    public UserModel findActiveUser(String username) throws UserNotFoundException {
        return userModelRepository
                .findActive(username)
                .orElseThrow(UserNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public List<UserModel> getUsers() {
        return userModelRepository.getAll();
    }

    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return userModelRepository.existsByEmail(email);
    }

    @Transactional(readOnly = true)
    public UserModel getUser(long userId) throws UserNotFoundException {
        return userModelRepository
                .getUser(userId)
                .orElseThrow(UserNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public void ensureExists(long id) throws UserNotFoundException {
        if (!userModelRepository.existsById(id))
            throw new UserNotFoundException();
    }

    @Transactional
    public UserModel createNewUser(UserModel newUserModel)
            throws CanNotCreateUserException {

        var user = userModelRepository.createUser(newUserModel);
        return user.orElseThrow(CanNotCreateUserException::new);
    }

}
