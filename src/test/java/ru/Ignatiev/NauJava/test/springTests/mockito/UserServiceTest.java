package ru.Ignatiev.NauJava.test.springTests.mockito;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.Ignatiev.NauJava.api.controller.BookController;
import ru.Ignatiev.NauJava.domain.entity.UserEntity;
import ru.Ignatiev.NauJava.domain.impl.BookRepositoryImpl;
import ru.Ignatiev.NauJava.domain.impl.UserServiceImpl;
import ru.Ignatiev.NauJava.domain.repo.UserRepository;
import ru.Ignatiev.NauJava.domain.repo.custom.BookRepositoryCustom;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;


    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void shouldRegisterUserSuccess() {
        String username = "testUser";
        String rawPassword = "password123";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        UserEntity result = userService.registerUser(username, rawPassword);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(username, result.getUsername());

        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    void shouldRegisterUserDuplicateThrowsException() {
        String username = "existingUser";
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(new UserEntity()));

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> userService.registerUser(username, "pass")
        );

        Assertions.assertEquals("User Duplicate found", exception.getMessage());
        verify(userRepository, never()).save(any());
    }

    @Test
    void shouldThrowExceptionWhenUsernameIsNull() {
        assertThrows(
                IllegalArgumentException.class,
                () -> userService.registerUser(null, "admin")
        );

        verify(userRepository, never()).save(any());
    }
}
