package ru.Ignatiev.NauJava.domain.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.Ignatiev.NauJava.config.UserDetailsServiceImpl;
import ru.Ignatiev.NauJava.domain.entity.Role;
import ru.Ignatiev.NauJava.domain.entity.UserEntity;
import ru.Ignatiev.NauJava.domain.repo.UserRepository;
import ru.Ignatiev.NauJava.domain.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserEntity registerUser(String username, String password) {
        if (!userRepository.findByUsername(username).isEmpty()) throw new RuntimeException("User Duplicate found");
        UserEntity newUser = new UserEntity();
        newUser.setUsername(username);
        newUser.setPassword(passwordEncoder.encode(password));
        newUser.addRoles(Role.USER);
        userRepository.save(newUser);

        return newUser;
    }

    public UserEntity findByUsername(String username) {
        var found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            return found.get();
        }
        else {
            log.error("User with username= {} not found", username);
            throw new ResourceNotFoundException();
        }
    }

}
