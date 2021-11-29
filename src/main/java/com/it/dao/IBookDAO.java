package com.it.dao;

import com.it.model.Book;
import java.util.List;

public interface IBookDAO {

        Book getBookByISBN(String isbn);
        void persistBook(Book book);
        void updateBook(Book book);
        Book getBookById(int id);
        List<Book> getBooksByCategory(Book.Category category);
        List<Book> getAllBooks();
        }
