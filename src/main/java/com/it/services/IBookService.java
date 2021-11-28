package com.it.services;

import com.it.model.Book;

import java.util.List;

public interface IBookService {

    void addBook(Book book);

    List<Book> getBooksByCategory(String category);
}
