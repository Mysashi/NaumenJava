package ru.Ignatiev.NauJava.domain.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import ru.Ignatiev.NauJava.domain.entity.AuthorEntity;
import java.util.List;

@Component
public interface AuthorRepository extends CrudRepository<AuthorEntity, Long> {

    List<AuthorEntity> findByNameAndSurnameAllIgnoreCase(String name, String surname);
}
