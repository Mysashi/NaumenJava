package ru.Ignatiev.NauJava.domain.service;

import org.springframework.stereotype.Service;
import ru.Ignatiev.NauJava.domain.entity.Book;
import ru.Ignatiev.NauJava.domain.repo.BookRepository;
import java.util.Comparator;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private int bookCounter;
    BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void addBookInList(Book book) {
        if (book.getId() == null) {
            bookCounter += 1;
            book.setId((long)bookCounter);
        }
        bookRepository.create(book);
    }

    @Override
    public void deleteBookById(Long id) {
        bookRepository.delete(id);
    }

    @Override
    public void updateBook(Book book) {
        bookRepository.update(book);
    }

    @Override
    public Book findBookById(Long id) {
        return bookRepository.read(id);
    }

    @Override
    public void showListOfBooks() {
        List<Book> books = bookRepository.findAll();
        books.forEach(System.out::println);
    }

    @Override
    public void sortBy(BookSortingMode bookSorting) {
        var sortedBookList = bookRepository.findAll().stream()
                .sorted(defineType(bookSorting))
                .toList();
        System.out.println("Sorted by " + bookSorting.name());
        sortedBookList.forEach(System.out::println);
    }

    private Comparator<Book> defineType(BookSortingMode type) {
        return switch (type) {
            case ID -> Comparator.comparing(Book::getId);
            case DATE -> Comparator.comparing(Book::getDateOfCreation);
        };
    }
}
