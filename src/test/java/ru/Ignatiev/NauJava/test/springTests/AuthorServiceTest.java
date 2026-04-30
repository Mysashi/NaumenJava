package ru.Ignatiev.NauJava.test.springTests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.Ignatiev.NauJava.domain.entity.AuthorEntity;
import ru.Ignatiev.NauJava.domain.entity.BookEntity;
import ru.Ignatiev.NauJava.domain.impl.AuthorServiceImpl;
import ru.Ignatiev.NauJava.domain.repo.AuthorRepository;
import ru.Ignatiev.NauJava.domain.repo.BookRepository;

@SpringBootTest
public class AuthorServiceTest {

    private static final Logger log = LoggerFactory.getLogger(AuthorServiceTest.class);
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final AuthorServiceImpl authorService;

    @Autowired
    public AuthorServiceTest(AuthorRepository authorRepository, BookRepository bookRepository, AuthorServiceImpl authorService) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.authorService = authorService;
    }

    @Test
    void deleteAuthorAndAssociatedBooksSuccessTransaction() {
        AuthorEntity author = new AuthorEntity();
        author.setName("Aleksey");
        author.setSurname("Ignatiev");
        author = authorRepository.save(author);

        BookEntity book1 = new BookEntity();
        book1.setName("Java Deep Dive");
        book1.setAuthor(author);

        BookEntity book2 = new BookEntity();
        book2.setName("Spring Boot Patterns");
        book2.setAuthor(author);

        bookRepository.save(book1);
        bookRepository.save(book2);

        authorService.deleteAuthor(author);

        var foundId = authorRepository.findById(author.getId());
        var foundBooks = bookRepository.findByAuthor(author);

        Assertions.assertFalse(foundId.isPresent());
        Assertions.assertTrue(foundBooks.isEmpty());
    }

    @Test
    void deleteAuthorAndAssociatedBooksRollbackTransaction() {
        AuthorEntity author = new AuthorEntity();
        author.setName("Pushkin");
        author = authorRepository.save(author);

        BookEntity book = new BookEntity();
        book.setName("Ruslan and Ludmila");
        book.setAuthor(author);
        bookRepository.save(book);

        AuthorEntity nullAuthor = new AuthorEntity();

        Assertions.assertThrows(Exception.class, () -> {
            authorService.deleteAuthor(nullAuthor);
        });


        Assertions.assertNotNull(authorRepository.findById(author.getId()));
        Assertions.assertFalse(bookRepository.findByAuthor(author).isEmpty());
    }
}
