package ru.Ignatiev.NauJava.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.Ignatiev.NauJava.domain.entity.BookEntity;
import ru.Ignatiev.NauJava.domain.repo.BookRepository;

@Controller
@RequestMapping("/book")
public class BookController {

    private BookRepository bookRepository;

    BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("/list")
    public String listAllBooks(Model model) {
        Iterable<BookEntity> books = bookRepository.findAll();
        model.addAttribute("books", books);
        return "index";
    }
}
