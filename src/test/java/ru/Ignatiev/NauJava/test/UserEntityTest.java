package ru.Ignatiev.NauJava.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.Ignatiev.NauJava.domain.entity.UserEntity;
import ru.Ignatiev.NauJava.domain.repo.UserRepository;

@SpringBootTest
public class UserEntityTest {

    private final UserRepository userRepository;

    @Autowired
    public UserEntityTest(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Test
    void shouldCreateUser() {
        UserEntity user = new UserEntity();
        user.setUsername("aleksej596");
        user.setPassword("qwerty");
        user.setName("Alexey");
        user.setSurname("Ignatiev");

        userRepository.save(user);

        var found = userRepository.findById(user.getId());

        Assertions.assertFalse(found.isEmpty());
        Assertions.assertEquals(found.get().getId(), user.getId());
        Assertions.assertEquals(found.get().getUsername(), user.getUsername());
    }


    @Test
    void shouldFindUserByUsername() {
        UserEntity user = new UserEntity();
        user.setUsername("ivan0v");
        user.setPassword("qwerty");
        user.setName("Ivan");
        user.setSurname("Ivanov");

        userRepository.save(user);

        var found = userRepository.findByUsername(user.getUsername());

        Assertions.assertFalse(found.isEmpty());
        Assertions.assertEquals(found.get().getId(), user.getId());
        Assertions.assertEquals(found.get().getUsername(), user.getUsername());
    }

}
