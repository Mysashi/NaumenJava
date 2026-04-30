package ru.Ignatiev.NauJava.domain.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "source")
public class SourceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "origin_type")
    private OriginType originType;

    @Column(name = "location")
    private String location;

    @Column(name = "books")
    @ManyToMany
    @JoinTable(name = "book_sources",
            joinColumns = @JoinColumn(name = "author_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "source_id", referencedColumnName = "id")
    )
    private Set<BookEntity> books;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public OriginType getOriginType() {
        return originType;
    }

    public void setOriginType(OriginType originType) {
        this.originType = originType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Set<BookEntity> getBooks() {
        return books;
    }

    public void setBooks(Set<BookEntity> books) {
        this.books = books;
    }
}
