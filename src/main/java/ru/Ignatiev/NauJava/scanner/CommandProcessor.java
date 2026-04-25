package ru.Ignatiev.NauJava.scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.Ignatiev.NauJava.domain.entity.Book;
import ru.Ignatiev.NauJava.domain.service.BookServiceImpl;
import ru.Ignatiev.NauJava.domain.service.BookSortingMode;

import java.util.Scanner;

@Component
public class CommandProcessor
{
    private final BookServiceImpl bookService;

    @Autowired
    public CommandProcessor(BookServiceImpl userService)
    {
        this.bookService = userService;
    }

    public void processCommand(String input)
    {
        Scanner scanner = new Scanner(System.in);
        String[] cmd = input.split(" ");
        switch (cmd[0])
        {
            case "create" -> scanCreate(scanner);
            case "delete" -> scanDelete(scanner);
            case "update" -> scanUpdate(scanner);
            case "read" -> scanRead(scanner);
            case "list" -> bookService.showListOfBooks();
            case "sort" -> scanSort(scanner);
            case "help" -> scanHelp();
            default -> System.out.println("Invalid command");
        }
    }

    private static void scanHelp() {
        System.out.println("Available commands: create, update, delete, read, list");
        System.out.println("create : creation of Book");
        System.out.println("update : update Book information");
        System.out.println("delete : delete Book from list");
        System.out.println("read : read Book information");
        System.out.println("list : retrieve all Books");
        System.out.println("sort : sort by some parameter.");
    }

    private void scanSort(Scanner scanner) {
        System.out.println("Type sort mode (1 - by id, 2 - by creation date)");
        String sortMode = scanner.nextLine();
        if (sortMode.equals("1")) {
            bookService.sortBy(BookSortingMode.ID);
        }
        else if (sortMode.equals("2")) {
            bookService.sortBy(BookSortingMode.DATE);
        }
    }

    private void scanCreate(Scanner scanner) {
        Book book = new Book();
        System.out.print("Type name of book: ");
        book.setName(scanner.nextLine());

        System.out.print("Type author name: ");
        book.setAuthorName(scanner.nextLine());

        System.out.print("Type genre: ");
        book.setGenre(scanner.nextLine());

        System.out.print("Type description: ");
        book.setDescription(scanner.nextLine());

        System.out.print("Type creation date: ");
        book.setDateOfCreation(Integer.parseInt(scanner.nextLine()));

        System.out.println("Added in library!");
        bookService.addBookInList(book);
    }

    private void scanDelete(Scanner scanner) {
        System.out.println("Type book id");
        bookService.deleteBookById(Long.valueOf(scanner.nextLine()));
    }

    private void scanRead(Scanner scanner) {
        System.out.println("Type book id");
        System.out.println(bookService.findBookById(Long.valueOf(scanner.nextLine())));
    }

    private void scanUpdate(Scanner scanner) {
        System.out.println("Type book id");
        long oldId = Long.parseLong(scanner.nextLine());
        Book book = new Book();
        book.setId(oldId);
        System.out.print("Type name of book: ");
        book.setName(scanner.nextLine());

        System.out.print("Type author name: ");
        book.setAuthorName(scanner.nextLine());

        System.out.print("Type genre: ");
        book.setGenre(scanner.nextLine());

        System.out.print("Type description: ");
        book.setDescription(scanner.nextLine());

        System.out.print("Type year of creation date: ");
        book.setDateOfCreation(Integer.parseInt(scanner.nextLine()));

        System.out.println("Updated book succesfully!");
        bookService.updateBook(book);
    }
}
