package com.it.model;

import javax.persistence.*;

@Entity(name = "tbook")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String author;
    private double price;
    private int pieces;
    private String isbn;
    @Enumerated(EnumType.STRING)
    private Category category;


    public Book() {
    }

    public Book(int id, String title, String author, double price, int pieces, String isbn, Category category) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.price = price;
        this.pieces = pieces;
        this.isbn = isbn;
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getPieces() {
        return pieces;
    }

    public void setPieces(int pieces) {
        this.pieces = pieces;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Category getCategory() {
        return category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public enum Category {
        JAVA,
        OTHER
    }

    @Override
    public Object clone() {
        return new Book(this.id, this.title, this.author, this.price, this.pieces, this.isbn, this.category);
    }
}
