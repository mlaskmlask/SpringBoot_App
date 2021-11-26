package com.it.dao;
import com.it.model.Book;

public interface IBookDAO {

   Book getBookByISBN (String isbn);
   void persistBook (Book book);
   void updateBook (Book book);
   Book getBookById (int id);
}
