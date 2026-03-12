package ru.Ignatiev.NauJava.domain.repo;

import org.springframework.stereotype.Component;
import ru.Ignatiev.NauJava.domain.entity.Book;

import java.util.List;

@Component
public class BookRepository implements CrudRepository<Book, Long>{

    private final List<Book> bookList;

    BookRepository(List<Book> bookList) {
        this.bookList = bookList;
    }

    public List<Book> findAll() {
        return bookList;
    }

    @Override
    public void create(Book entity) {
        bookList.add(entity);
        System.out.println("Book with id=" + entity.getId() + " was added to library");
    }

    @Override
    public Book read(Long id) {
        for (Book book : bookList) {
            if (book.getId().equals(id)) {
                System.out.println("Book with id=" + book.getId() + " was found");
                return book;
            }
        }
        throw new RuntimeException("Book with id=" + id + "was not found in database");
    }

    @Override
    public void update(Book newBook) {
        for (int i = 0; i < bookList.size(); i++) {
            var bookOld = bookList.get(i);
            if (bookOld.getId().equals(newBook.getId())) {
                bookList.set(i, newBook);
                System.out.println("Book with id=" + newBook.getId() + " was updated");
                return;
            }
        }
    }

    @Override
    public void delete(Long id) {
        bookList.removeIf(book -> book.getId().equals(id));
    }
}
