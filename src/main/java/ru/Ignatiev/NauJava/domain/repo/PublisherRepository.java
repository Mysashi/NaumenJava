package ru.Ignatiev.NauJava.domain.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import ru.Ignatiev.NauJava.domain.entity.PublisherEntity;

@Component
public interface PublisherRepository extends CrudRepository<PublisherEntity, Long> {
}
