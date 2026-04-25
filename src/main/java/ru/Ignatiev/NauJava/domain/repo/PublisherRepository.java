package ru.Ignatiev.NauJava.domain.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Component;
import ru.Ignatiev.NauJava.domain.entity.PublisherEntity;

@Component
@RepositoryRestResource(path = "publisher")
public interface PublisherRepository extends CrudRepository<PublisherEntity, Long> {
}
