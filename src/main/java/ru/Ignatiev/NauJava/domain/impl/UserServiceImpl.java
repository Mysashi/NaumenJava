package ru.Ignatiev.NauJava.domain.impl;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.Ignatiev.NauJava.domain.entity.Role;
import ru.Ignatiev.NauJava.domain.entity.UserEntity;
import ru.Ignatiev.NauJava.domain.repo.UserRepository;
import ru.Ignatiev.NauJava.domain.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PasswordEncoder encoder;

    @Autowired
    UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder,
                    PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.encoder = encoder;
    }


    @PostConstruct
    public void init() {
        UserEntity admin = new UserEntity();
        admin.setUsername("admin");
        admin.setPassword(encoder.encode("admin"));
        admin.addRoles(Role.ADMIN);
        userRepository.save(admin);
    }

    @Override
    public UserEntity registerUser(String username, String password) {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        if (userRepository.findByUsername(username).isPresent()) throw new RuntimeException("User Duplicate found");
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
