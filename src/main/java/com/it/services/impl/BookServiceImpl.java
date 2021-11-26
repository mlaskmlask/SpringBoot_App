package com.it.services.impl;

import com.it.dao.IBookDAO;
import com.it.database.IBookRepository;
import com.it.model.Book;
import com.it.services.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements IBookService {

    @Autowired
    IBookDAO bookDAO;

    @Override
    public void addBook(Book book) {
        Book bookFromDB = this.bookDAO.getBookByISBN(book.getIsbn());
        if (bookFromDB == null) {
            this.bookDAO.persistBook(book);
        } else {
            bookFromDB.setPieces(bookFromDB.getPieces() + book.getPieces());
            this.bookDAO.updateBook(bookFromDB);
        }
    }
}
