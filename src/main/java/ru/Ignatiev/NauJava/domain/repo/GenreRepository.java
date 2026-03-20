package ru.Ignatiev.NauJava.domain.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import ru.Ignatiev.NauJava.domain.entity.GenreEntity;

@Component
public interface GenreRepository extends CrudRepository<GenreEntity, Long> {

}
