package com.utils;

import com.it.model.Book;

import java.util.ArrayList;
import java.util.List;

public class FilterUtils {
    public static List<Book> filteredBooks(List<Book> books, String filter) {
        if(filter==null){
            return books;
        }
        List<Book> filteredBooks = new ArrayList<>();
        for (Book currentBook : books){
            if(currentBook.getTitle().toUpperCase().contains(filter.toUpperCase())||
                    currentBook.getAuthor().toUpperCase().contains(filter.toUpperCase())){
                filteredBooks.add(currentBook);
            }
        }
        return filteredBooks;
    }
}
