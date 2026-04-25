package ru.Ignatiev.NauJava.domain.repo.custom;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Component;
import ru.Ignatiev.NauJava.domain.entity.AuthorEntity;
import java.util.List;


@RepositoryRestResource(path = "customAuthor")
public interface AuthorRepositoryCustom {
    List<AuthorEntity> findByNameAndSurnameAllIgnoreCase(String name, String surname);
}
