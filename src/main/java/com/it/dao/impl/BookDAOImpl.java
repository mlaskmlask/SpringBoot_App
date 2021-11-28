package com.it.dao.impl;

import com.it.dao.IBookDAO;
import com.it.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BookDAOImpl implements IBookDAO {

    @Autowired
    Connection connection;

    @Override
    public Book getBookByISBN(String isbn) {
        try{
            String SQL = "SELECT * FROM tbook WHERE isbn=?";
            PreparedStatement preparedStatement = this.connection.prepareStatement(SQL);
            preparedStatement.setString(1, isbn);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return this.mapResultSetToBook(resultSet);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public void persistBook(Book book) {
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

    @Override
    public void updateBook(Book book) {
        try{
            String SQL = "UPDATE tbook SET title=?, author=?, pieces=?, isbn=?, price=?, category=? WHERE id=?";
            PreparedStatement preparedStatement = this.connection.prepareStatement(SQL);
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2, book.getAuthor());
            preparedStatement.setInt(3,book.getPieces());
            preparedStatement.setString(4, book.getIsbn());
            preparedStatement.setDouble(5,book.getPrice());
            preparedStatement.setString(6, book.getCategory().toString());
            preparedStatement.setInt(7, book.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public Book getBookById(int id) {
        try{
            String SQL = "SELECT * FROM tbook WHERE id=?";
            PreparedStatement preparedStatement = this.connection.prepareStatement(SQL);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return this.mapResultSetToBook(resultSet);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Book> getBooksByCategory(Book.Category category) {
        List <Book> books = new ArrayList<>();
        try{
            String SQL = "SELECT * FROM tbook WHERE category=?";
            PreparedStatement preparedStatement = this.connection.prepareStatement(SQL);
            preparedStatement.setString(1, category.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                books.add(this.mapResultSetToBook(resultSet));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return books;
    }

    @Override
    public List<Book> getAllBooks() {
        List <Book> books = new ArrayList<>();
        try{
            String SQL = "SELECT * FROM tbook";
            PreparedStatement preparedStatement = this.connection.prepareStatement(SQL);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                books.add(this.mapResultSetToBook(resultSet));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return books;
    }

    private Book mapResultSetToBook(ResultSet resultSet) throws SQLException{
        Book book = new Book();
        book.setId(resultSet.getInt("id"));
        book.setTitle(resultSet.getString("title"));
        book.setAuthor(resultSet.getString("author"));
        book.setPieces(resultSet.getInt("pieces"));
        book.setIsbn(resultSet.getString("isbn"));
        book.setPrice(resultSet.getDouble("price"));
        book.setCategory(Book.Category.valueOf(resultSet.getString("category")));
        return book;
    }
}
