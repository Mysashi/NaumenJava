package ru.Ignatiev.NauJava.domain.repo;

import ru.Ignatiev.NauJava.domain.entity.Book;

public interface CrudRepository<E, I> {
    void create(E entity);
    Book read(I id);
    void update(E entity);
    void delete(I id);
}
