package com.it.dao.impl;

import com.it.dao.IBookDAO;
import com.it.model.Book;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class HibernateBookDAO implements IBookDAO {

   @Autowired
  SessionFactory sessionFactory;

    @Override
    public Book getBookByISBN(String isbn) {
        Session session = this.sessionFactory.openSession();
        Query<Book> query = session.createQuery("FROM com.it.model.Book WHERE isbn= :isbn");
        query.setParameter("isbn", isbn);
        Book book = query.getSingleResult();
        session.close();
        return book;
    }

    @Override
    public void persistBook(Book book) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(book);
            tx.commit();
        } catch (Exception e) {
            if (tx != null)
                tx.rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public void updateBook(Book book) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(book);
            tx.commit();
        } catch (Exception e) {
            if (tx != null)
                tx.rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public Book getBookById(int id) {
        Session session = this.sessionFactory.openSession();
        Query<Book> query = session.createQuery("FROM com.it.model.Book WHERE id= :id");
        query.setParameter("id", id);
        Book book = query.getSingleResult();
        session.close();
        return book;
    }

    @Override
    public List<Book> getBooksByCategory(Book.Category category) {
        Session session = this.sessionFactory.openSession();
        Query<Book> query = session.createQuery("FROM com.it.model.Book WHERE category= :category");
        query.setParameter("category", category);
        List<Book> listBooks = query.getResultList();
        session.close();
        return listBooks;
    }

    @Override
    public List<Book> getAllBooks() {
        Session session = this.sessionFactory.openSession();
        Query<Book> query = session.createQuery("FROM com.it.model.Book");
        List<Book> listAllBooks = query.getResultList();
        session.close();
        return listAllBooks;
    }
}
