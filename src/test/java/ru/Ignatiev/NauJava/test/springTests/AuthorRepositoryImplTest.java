package ru.Ignatiev.NauJava.test.springTests;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.Ignatiev.NauJava.domain.entity.AuthorEntity;
import ru.Ignatiev.NauJava.domain.impl.AuthorRepositoryImpl;

import java.util.List;

@SpringBootTest
@Transactional
public class AuthorRepositoryImplTest {

    private final EntityManager entityManager;

    private final AuthorRepositoryImpl authorRepositoryImpl;

    @Autowired
    public AuthorRepositoryImplTest(EntityManager entityManager, AuthorRepositoryImpl authorRepositoryImpl) {
        this.entityManager = entityManager;
        this.authorRepositoryImpl = authorRepositoryImpl;
    }

    @BeforeEach
    void setUp() {
        saveAuthor("Alexey", "Ignatiev");
        saveAuthor("Andrey", "IVANOV");
        saveAuthor("IVAN", "Lopuhov");
    }

    private void saveAuthor(String name, String surname) {
        AuthorEntity author = new AuthorEntity();
        author.setName(name);
        author.setSurname(surname);
        entityManager.persist(author);
        entityManager.flush();
    }

    @Test
    void shouldFindByNameAndSurnameIgnoringCase() {
        List<AuthorEntity> results = authorRepositoryImpl.findByNameAndSurnameAllIgnoreCase("alexey", "IGNATIEV");
        Assertions.assertEquals(1, results.size());
        Assertions.assertEquals("Alexey", results.getFirst().getName());
        Assertions.assertEquals("Ignatiev", results.getFirst().getSurname());
    }
}
