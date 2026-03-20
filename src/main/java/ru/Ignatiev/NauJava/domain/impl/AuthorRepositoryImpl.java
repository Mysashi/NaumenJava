package ru.Ignatiev.NauJava.domain.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.Ignatiev.NauJava.domain.entity.AuthorEntity;
import ru.Ignatiev.NauJava.domain.repo.custom.AuthorRepositoryCustom;
import java.util.List;

@Repository
public class AuthorRepositoryImpl implements AuthorRepositoryCustom {

    private final EntityManager entityManager;

    public AuthorRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<AuthorEntity> findByNameAndSurnameAllIgnoreCase(String name, String surname) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<AuthorEntity> cq = cb.createQuery(AuthorEntity.class);
        Root<AuthorEntity> root = cq.from(AuthorEntity.class);

        Predicate namePredicate = cb.equal(
                cb.lower(root.get("name")),
                name.toLowerCase()
        );

        Predicate surnamePredicate = cb.equal(
                cb.lower(root.get("surname")),
                surname.toLowerCase()
        );

        cq.select(root)
                .where(cb.and(namePredicate, surnamePredicate))
                .orderBy(cb.asc(root.get("name")));

        return entityManager.createQuery(cq).getResultList();
    }


}
