package ru.Ignatiev.NauJava.domain.entity;

public class Book {

    private Long id;

    private String name;

    private String authorName;

    private String genre;

    private String description;

    private int dateOfCreation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDateOfCreation() { return dateOfCreation; }

    public void setDateOfCreation(int dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    @Override
    public String toString() {
        return "Book id: " + getId() +
                ", Book name: " + getName() +
                ", Book description: " + getDescription() +
                ", Author of book: " + getAuthorName() +
                ", Creation date: " + getDateOfCreation();
    }
}
