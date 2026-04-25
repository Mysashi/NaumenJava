package ru.Ignatiev.NauJava.domain.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import ru.Ignatiev.NauJava.domain.entity.AuthorEntity;
import ru.Ignatiev.NauJava.domain.entity.BookEntity;
import ru.Ignatiev.NauJava.domain.repo.AuthorRepository;
import ru.Ignatiev.NauJava.domain.repo.BookRepository;
import ru.Ignatiev.NauJava.domain.service.AuthorService;
import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    private static final Logger log = LoggerFactory.getLogger(AuthorServiceImpl.class);
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final PlatformTransactionManager transactionManager;

    public AuthorServiceImpl(AuthorRepository authorRepository, BookRepository bookRepository, PlatformTransactionManager transactionManager) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.transactionManager = transactionManager;
    }

    @Override
    public void deleteAuthor(AuthorEntity author) {
        TransactionStatus status = transactionManager.getTransaction(new
                DefaultTransactionDefinition());
        try {
            List<BookEntity> books = bookRepository.findByAuthor(author);
            for (BookEntity book: books) {
                bookRepository.delete(book);
            }
            authorRepository.delete(author);
            transactionManager.commit(status);
            log.info("Transaction has passed successfully, deleted author={}", author);
        }
        catch (DataAccessException ex) {
            log.info("Doing rollback. Error={}", ex.getMessage());
            transactionManager.rollback(status);
            throw ex;
        }
    }

}
