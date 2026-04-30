package ru.Ignatiev.NauJava.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.Ignatiev.NauJava.domain.entity.Role;
import ru.Ignatiev.NauJava.domain.entity.UserEntity;
import ru.Ignatiev.NauJava.domain.repo.UserRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    UserDetailsServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.userRepository = userRepository;
        setExampleUsers(passwordEncoder);
    }

    private void setExampleUsers(PasswordEncoder passwordEncoder) {
        UserEntity admin = new UserEntity();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("admin"));
        admin.addRoles(Role.ADMIN);
        userRepository.save(admin);

        UserEntity user = new UserEntity();
        user.setUsername("user");
        user.setPassword(passwordEncoder.encode("user"));
        user.addRoles(Role.USER);
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

}
