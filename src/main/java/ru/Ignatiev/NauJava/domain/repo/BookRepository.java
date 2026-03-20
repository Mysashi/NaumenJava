package ru.Ignatiev.NauJava.domain.repo;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import ru.Ignatiev.NauJava.domain.entity.AuthorEntity;
import ru.Ignatiev.NauJava.domain.entity.BookEntity;

import java.util.List;


@Component
public interface BookRepository extends CrudRepository<BookEntity, Long> {

    @Query("SELECT b FROM BookEntity b WHERE b.publicationYear BETWEEN :minYear AND :maxYear")
    List<BookEntity> findBooksByYearRange(@Param("minYear") int minYear,
                                          @Param("maxYear") int maxYear);

    List<BookEntity> findByAuthor(AuthorEntity author);

    BookEntity findByName(String name);
}
