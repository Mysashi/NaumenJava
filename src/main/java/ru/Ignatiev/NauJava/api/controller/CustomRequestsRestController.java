package ru.Ignatiev.NauJava.api.controller;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.Ignatiev.NauJava.domain.entity.AuthorEntity;
import ru.Ignatiev.NauJava.domain.entity.BookEntity;
import ru.Ignatiev.NauJava.domain.repo.custom.AuthorRepositoryCustom;
import ru.Ignatiev.NauJava.domain.repo.custom.BookRepositoryCustom;

import java.util.List;

@RestController
@RequestMapping("/api/custom")
public class CustomRequestsRestController {

    private BookRepositoryCustom bookRepositoryCustom;
    private AuthorRepositoryCustom authorRepositoryCustom;

    CustomRequestsRestController(BookRepositoryCustom bookRepositoryCustom, AuthorRepositoryCustom authorRepositoryCustom) {
        this.bookRepositoryCustom = bookRepositoryCustom;
        this.authorRepositoryCustom = authorRepositoryCustom;
    }

    @GetMapping("/author/findByNameAndSurname")
    private List<AuthorEntity> findAuthorsByNameAndSurname(@RequestParam String name, @RequestParam String surname) {
        var found = authorRepositoryCustom.findByNameAndSurnameAllIgnoreCase(name, surname);
        if (found.isEmpty()) throw new ResourceNotFoundException();
        return found;
    }


    @GetMapping("/book/findBookByYearRange")
    private List<BookEntity> findBookByYearRange(@RequestParam int minYear, @RequestParam int maxYear) {
        var found = bookRepositoryCustom.findBooksByYearRange(minYear, maxYear);
        if (found.isEmpty()) throw new ResourceNotFoundException();
        return found;
    }
}
