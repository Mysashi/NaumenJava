package ru.Ignatiev.NauJava.domain.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import ru.Ignatiev.NauJava.domain.entity.SourceEntity;

@Component
public interface SourceRepository extends CrudRepository<SourceEntity, Long> {
}
