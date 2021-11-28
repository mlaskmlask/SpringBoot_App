package com.it.services.impl;

import com.it.dao.IBookDAO;
import com.it.database.IBookRepository;
import com.it.model.Book;
import com.it.services.IBookService;
import com.it.session.SessionObject;
import com.utils.FilterUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BookServiceImpl implements IBookService {

    @Autowired
    IBookDAO bookDAO;

    @Resource
    SessionObject sessionObject;

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

    @Override
    public List<Book> getBooksByCategory(String category) {
        switch (category) {
            case "java":
                return FilterUtils.filteredBooks(this.bookDAO.getBooksByCategory(Book.Category.JAVA),
                        this.sessionObject.getFilter());
            case "other":
                return FilterUtils.filteredBooks(this.bookDAO.getBooksByCategory(Book.Category.OTHER),
                        this.sessionObject.getFilter());
            default:
                return FilterUtils.filteredBooks(this.bookDAO.getAllBooks(),
                        this.sessionObject.getFilter());
        }
    }
}
