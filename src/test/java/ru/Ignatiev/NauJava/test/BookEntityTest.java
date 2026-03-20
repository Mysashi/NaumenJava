package ru.Ignatiev.NauJava.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.Ignatiev.NauJava.domain.entity.BookEntity;
import ru.Ignatiev.NauJava.domain.repo.BookRepository;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@SpringBootTest
public class BookEntityTest {
    private final BookRepository bookRepository;
    private final int RANDOM_MIN_YEAR = 1900;
    private final int RANDOM_MAX_YEAR = 2025;

    @Autowired
    public BookEntityTest(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Test
    void testFindByName() {
        String bookName = UUID.randomUUID().toString();
        BookEntity book = new BookEntity();
        book.setName(bookName);

        bookRepository.save(book);

        var found = bookRepository.findByName(bookName);

        Assertions.assertNotNull(found);
        Assertions.assertEquals(book.getId(), found.getId());
        Assertions.assertEquals(book.getName(), found.getName());
    }

    @Test
    void testCreateBook() {
        BookEntity book = new BookEntity();
        book.setName("Война и мир");

        bookRepository.save(book);

        var found = bookRepository.findByName("Война и мир");

        Assertions.assertNotNull(found);
    }

    @Test
    void testFindBooksByYearRange() {
        String bookName = UUID.randomUUID().toString();
        int yearOfCreation = ThreadLocalRandom.current().nextInt(RANDOM_MIN_YEAR, RANDOM_MAX_YEAR);
        BookEntity book = new BookEntity();
        book.setName(bookName);
        book.setYearOfCreation(yearOfCreation);
        bookRepository.save(book);

        var found = bookRepository.findBooksByYearRange(RANDOM_MIN_YEAR, RANDOM_MAX_YEAR).getFirst();

        Assertions.assertNotNull(found);
        Assertions.assertEquals(found.getId(), book.getId());
        Assertions.assertEquals(found.getYearOfCreation(), book.getYearOfCreation());
    }


    // Вопрос: если тестируется только BookEntity, то можно ли добавлять репозитории других сущностей,
    // от которых зависит BookEntity?(AuthorEntity, GenreEntity)
}
