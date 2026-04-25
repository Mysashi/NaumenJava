package ru.Ignatiev.NauJava.domain.repo;
public interface CrudRepository<E, I> {
    void create(E entity);
    E read(I id);
    void update(E entity);
    void delete(I id);
}
