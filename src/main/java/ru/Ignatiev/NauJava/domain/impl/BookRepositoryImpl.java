package ru.Ignatiev.NauJava.domain.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.Ignatiev.NauJava.domain.entity.BookEntity;
import ru.Ignatiev.NauJava.domain.repo.custom.BookRepositoryCustom;

import java.util.List;

@Repository
public class BookRepositoryImpl implements BookRepositoryCustom {

    private final EntityManager entityManager;

    public BookRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<BookEntity> findBooksByYearRange(int minYear, int maxYear) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<BookEntity> cq = cb.createQuery(BookEntity.class);
        Root<BookEntity> root = cq.from(BookEntity.class);

        Predicate yearPredicate = cb.between(
                root.get("yearOfCreation"),
                minYear,
                maxYear
        );

        cq.select(root)
                .where(yearPredicate)
                .orderBy(cb.asc(root.get("yearOfCreation")));

        return entityManager.createQuery(cq).getResultList();
    }
}
