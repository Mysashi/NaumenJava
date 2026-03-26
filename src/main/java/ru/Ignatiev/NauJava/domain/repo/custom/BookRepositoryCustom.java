package ru.Ignatiev.NauJava.domain.repo.custom;

import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Component;
import ru.Ignatiev.NauJava.domain.entity.BookEntity;

import java.util.List;

@Component
@RepositoryRestResource(path = "customBook")
public interface BookRepositoryCustom {
    List<BookEntity> findBooksByYearRange(@Param("minYear") int minYear, @Param("maxYear") int maxYear);
}
