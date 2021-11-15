package com.it.database;

import com.it.model.Book;

import java.util.List;

public interface IBookRepository {

    List<Book> getAllBooks();
    List<Book> getJavaBooks();
    List<Book> getOtherBooks();
    List<Book> getBooksByFilter(String filter);
}
