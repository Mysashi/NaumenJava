package ru.Ignatiev.NauJava.domain.service;

import ru.Ignatiev.NauJava.domain.entity.Book;


public interface BookService {

    void addBookInList(Book book);

    void deleteBookById(Long id);

    void updateBook(Book book);

    Book findBookById(Long id);

    void showListOfBooks();

    void sortBy(BookSortingMode bookSortingMode);

}
