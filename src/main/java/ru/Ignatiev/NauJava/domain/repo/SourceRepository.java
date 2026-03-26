package ru.Ignatiev.NauJava.domain.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Component;
import ru.Ignatiev.NauJava.domain.entity.SourceEntity;

@Component
@RepositoryRestResource(path = "source")
public interface SourceRepository extends CrudRepository<SourceEntity, Long> {
}
