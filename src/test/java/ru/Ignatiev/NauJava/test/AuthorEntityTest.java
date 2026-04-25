package ru.Ignatiev.NauJava.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.Ignatiev.NauJava.domain.entity.AuthorEntity;
import ru.Ignatiev.NauJava.domain.repo.AuthorRepository;

import java.util.UUID;

@SpringBootTest
public class AuthorEntityTest {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorEntityTest(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Test
    void findByNameAndSurnameAllIgnoreCase() {
        String authorName = UUID.randomUUID().toString();
        String authorSurname = UUID.randomUUID().toString();
        AuthorEntity author = new AuthorEntity();
        author.setName(authorName);
        author.setSurname(authorSurname);

        authorRepository.save(author);

        var found = authorRepository.findByNameAndSurnameAllIgnoreCase(authorName, authorSurname).getFirst();

        Assertions.assertNotNull(found);
        Assertions.assertEquals(found.getId(), author.getId());
        Assertions.assertEquals(found.getName(), author.getName());
        Assertions.assertEquals(found.getSurname(), author.getSurname());
    }
}
