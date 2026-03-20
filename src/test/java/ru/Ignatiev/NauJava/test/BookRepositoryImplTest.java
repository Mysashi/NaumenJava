package ru.Ignatiev.NauJava.test;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.Ignatiev.NauJava.domain.entity.BookEntity;
import ru.Ignatiev.NauJava.domain.impl.BookRepositoryImpl;

import java.util.List;

@SpringBootTest
@Transactional
public class BookRepositoryImplTest {


    private final EntityManager entityManager;
    private final BookRepositoryImpl bookRepositoryImpl;

    @Autowired
    public BookRepositoryImplTest(EntityManager entityManager, BookRepositoryImpl bookRepositoryImpl) {
        this.entityManager = entityManager;
        this.bookRepositoryImpl = bookRepositoryImpl;
    }

    @BeforeEach
    void setUp() {
        saveBook("Book 1", 1990);
        saveBook("Book 2", 2005);
    }

    private void saveBook(String name, int yearOfCreation) {
        BookEntity book = new BookEntity();
        book.setName(name);
        book.setYearOfCreation(yearOfCreation);
        entityManager.persist(book);
        entityManager.flush();
    }

    @Test
    void shouldFindBooksWithinYearRange() {
        List<BookEntity> results = bookRepositoryImpl.findBooksByYearRange(2000, 2010);
        Assertions.assertNotNull(results);
        Assertions.assertEquals(1, results.size());
    }
}
