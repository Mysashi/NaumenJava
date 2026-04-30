package ru.Ignatiev.NauJava.domain.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Component;
import ru.Ignatiev.NauJava.domain.entity.UserEntity;

import java.util.Optional;

@Component
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);
}
