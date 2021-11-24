package com.it.database.impl;

import com.it.database.IBookRepository;
import com.it.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JDBCBookRepositoryImpl implements IBookRepository {

    @Autowired
    Connection connection;

    @Override
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList();
        try {
            String SQL = "SELECT * FROM tbook";
            PreparedStatement preparedStatement = this.connection.prepareStatement(SQL);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getInt("id"));
                book.setTitle(resultSet.getString("title"));
                book.setAuthor(resultSet.getString("author"));
                book.setPieces(resultSet.getInt("pieces"));
                book.setIsbn(resultSet.getString("isbn"));
                book.setPrice(resultSet.getDouble("price"));
                book.setCategory(Book.Category.valueOf(resultSet.getString("category")));
                books.add(book);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return books;
    }

    @Override
    public List<Book> getJavaBooks() {
        List<Book> javaBooks = new ArrayList<>();
        try {
            String SQL = "SELECT * FROM tbook WHERE category=?";
            PreparedStatement preparedStatement = this.connection.prepareStatement(SQL);
            preparedStatement.setString(1, Book.Category.JAVA.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getInt("id"));
                book.setTitle(resultSet.getString("title"));
                book.setAuthor(resultSet.getString("author"));
                book.setPieces(resultSet.getInt("pieces"));
                book.setIsbn(resultSet.getString("isbn"));
                book.setPrice(resultSet.getDouble("price"));
                book.setCategory(Book.Category.valueOf(resultSet.getString("category")));
                javaBooks.add(book);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return javaBooks;
    }

    @Override
    public List<Book> getOtherBooks() {
        List<Book> otherBooks = new ArrayList<>();
        try {
            String SQL = "SELECT * FROM tbook WHERE category=?";
            PreparedStatement preparedStatement = this.connection.prepareStatement(SQL);
            preparedStatement.setString(1, Book.Category.OTHER.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getInt("id"));
                book.setTitle(resultSet.getString("title"));
                book.setAuthor(resultSet.getString("author"));
                book.setPieces(resultSet.getInt("pieces"));
                book.setIsbn(resultSet.getString("isbn"));
                book.setPrice(resultSet.getDouble("price"));
                book.setCategory(Book.Category.valueOf(resultSet.getString("category")));
                otherBooks.add(book);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return otherBooks;
    }

    @Override
    public Book getBookByISBN(String isbn) {
        try {
            String SQL = "SELECT * FROM tbook WHERE isbn=?";
            PreparedStatement preparedStatement = this.connection.prepareStatement(SQL);
            preparedStatement.setString(1, isbn);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getInt("id"));
                book.setTitle(resultSet.getString("title"));
                book.setAuthor(resultSet.getString("author"));
                book.setPieces(resultSet.getInt("pieces"));
                book.setIsbn(resultSet.getString("isbn"));
                book.setPrice(resultSet.getDouble("price"));
                book.setCategory(Book.Category.valueOf(resultSet.getString("category")));
                return book;

            } else {
                return null;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public void addBook(Book book) {
        try {
            String SQL = "INSERT INTO tbook (title,author,pieces,isbn,price,category) VALUES (?,?,?,?,?,?)";
            PreparedStatement preparedStatement = this.connection.prepareStatement(SQL);
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2, book.getAuthor());
            preparedStatement.setInt(3, book.getPieces());
            preparedStatement.setString(4, book.getIsbn());
            preparedStatement.setDouble(5, book.getPrice());
            preparedStatement.setString(6, book.getCategory().toString());
            preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
