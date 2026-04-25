package ru.Ignatiev.NauJava.domain.repo.custom;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import ru.Ignatiev.NauJava.domain.entity.BookEntity;

import java.util.List;

@Component
public interface BookRepositoryCustom {
    List<BookEntity> findBooksByYearRange(@Param("minYear") int minYear, @Param("maxYear") int maxYear);
}
