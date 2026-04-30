package ru.Ignatiev.NauJava.domain.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Component;
import ru.Ignatiev.NauJava.domain.entity.ReportEntity;

@Component
@RepositoryRestResource(path = "report")
public interface ReportRepository extends CrudRepository<ReportEntity, Long> {
}
