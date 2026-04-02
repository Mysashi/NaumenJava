package ru.Ignatiev.NauJava.domain.service;

import ru.Ignatiev.NauJava.domain.entity.UserEntity;

public interface UserService {
    UserEntity registerUser(String username, String password);

    UserEntity findByUsername(String username);
}
