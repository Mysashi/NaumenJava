package ru.Ignatiev.NauJava.domain.repo.custom;

import org.springframework.stereotype.Component;
import ru.Ignatiev.NauJava.domain.entity.AuthorEntity;
import java.util.List;

@Component
public interface AuthorRepositoryCustom {
    List<AuthorEntity> findByNameAndSurnameAllIgnoreCase(String name, String surname);
}
