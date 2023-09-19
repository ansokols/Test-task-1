package main.DTO;

import java.time.Year;

public class Book {
    private Integer bookId;
    private String name;
    private String author;
    private String genre;
    private String language;
    private String publisher;
    private Year year;

    public Book(Integer bookId, String name, String author, String genre, String language, String publisher, Year year) {
        this.bookId = bookId;
        this.name = name;
        this.author = author;
        this.genre = genre;
        this.language = language;
        this.publisher = publisher;
        this.year = year;
    }

    public Integer getBookId() {
        return bookId;
    }
    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getLanguage() {
        return language;
    }
    public void setLanguage(String language) {
        this.language = language;
    }

    public String getPublisher() {
        return publisher;
    }
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Year getYear() {
        return year;
    }
    public void setYear(Year year) {
        this.year = year;
    }
}
