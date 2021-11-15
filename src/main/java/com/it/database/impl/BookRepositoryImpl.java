package com.it.database.impl;

import com.it.database.IBookRepository;
import com.it.model.Book;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookRepositoryImpl implements IBookRepository {
    private final List<Book> books = new ArrayList<>();

    public BookRepositoryImpl() {
        books.add(new Book("Algorytmy. Ilustrowany przewodnik", "Aditya Bhargava", 43.91, 20, "978-83-283-3445-8", Book.Category.OTHER));
        books.add(new Book("Czysty kod. Podręcznik dobrego programisty", "Robert C. Martin", 55.29, 30, "978-83-283-0234-1", Book.Category.OTHER));
        books.add(new Book("Java. Podstawy. Wydanie XI", "Cay S. Horstmann", 79.25, 25, "978-83-283-5778-5", Book.Category.JAVA));
        books.add(new Book("Python. Wprowadzenie. Wydanie V", "Mark Lutz", 169, 30, "978-83-283-6150-8", Book.Category.OTHER));
        books.add(new Book("Język C++. Szkoła programowania. Wydanie VI", "Stephen Prata", 59.43, 23, "978-83-246-4336-3", Book.Category.OTHER));
        books.add(new Book("Java. Efektywne programowanie. Wydanie III", "Joshua Bloch", 63.25, 15, "978-83-283-4576-8", Book.Category.JAVA));
    }

    @Override
    public List<Book> getAllBooks() {

        return this.books;
    }

    @Override
    public List<Book> getJavaBooks() {
        List<Book> javaBooks = new ArrayList<>();
        for (Book book : this.books) {
            if (book.getCategory() == Book.Category.JAVA) {
                javaBooks.add(book);
            }
        }
        return javaBooks;
    }

    @Override
    public List<Book> getOtherBooks() {
        List<Book> otherBooks = new ArrayList<>();
        for (Book book : this.books) {
            if (book.getCategory() == Book.Category.OTHER) {
                otherBooks.add(book);
            }
        }
        return otherBooks;
    }

    @Override
    public List<Book> getBooksByFilter(String filter) {
        List<Book> filteredBooks = new ArrayList<>();
        for (Book currentBook : this.books){
            if(currentBook.getTitle().toUpperCase().contains(filter.toUpperCase())||
            currentBook.getAuthor().toUpperCase().contains(filter.toUpperCase())){
                filteredBooks.add(currentBook);
            }
        }
        return filteredBooks;
    }
}
