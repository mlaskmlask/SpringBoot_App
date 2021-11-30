package com.it.services.impl;

import com.it.dao.IBookDAO;
import com.it.dao.IOrderDAO;
import com.it.model.Book;
import com.it.model.Order;
import com.it.model.OrderPosition;
import com.it.services.IBasketService;
import com.it.session.SessionObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BasketServiceImpl implements IBasketService {

    @Resource
    SessionObject sessionObject;

    @Autowired
    IBookDAO bookDAO;
    @Autowired
    IOrderDAO orderDAO;

    @Override
    public void addToBasket(int id) {
        for (Book book : this.sessionObject.getBasket()) {
            if (book.getId() == id) {
                book.setPieces(book.getPieces() + 1);
                return;
            }
        }
        Book book = this.bookDAO.getBookById(id);
        book.setPieces(1);
        this.sessionObject.getBasket().add(book);
    }

    @Override
    public double calculateBill() {
        double bill = 0;
        for (Book book : this.sessionObject.getBasket()) {
            bill = bill + book.getPrice() * book.getPieces();
        }
        return bill;
    }

    @Override
    public void removeBookFromBasket(int id) {
        for (Book book : this.sessionObject.getBasket()) {
            if (book.getId() == id) {
                this.sessionObject.getBasket().remove(book);
                return;
            }
        }
    }

    @Override
    public void confirmOrder() {
        List<Book> orderedBooks = this.sessionObject.getBasket();
        Order order = new Order();
        order.setUser(this.sessionObject.getUser());
        double bill = 0;
        for (Book book : orderedBooks) {
            bill = bill + book.getPrice() * book.getPieces();
        }
        order.setPrice(bill);
        order.setStatus(Order.Status.ORDERED);

        for (Book book : orderedBooks) {
            OrderPosition orderPosition = new OrderPosition();
            orderPosition.setPieces(book.getPieces());
            orderPosition.setOrder(order);
            orderPosition.setBook(book);

            order.getPositions().add(orderPosition);
        }
        this.orderDAO.persistOrder(order);

        for (Book book : orderedBooks) {
            Book bookFromDatabase = this.bookDAO.getBookById(book.getId());
            bookFromDatabase.setPieces(bookFromDatabase.getPieces() - book.getPieces());
            this.bookDAO.updateBook(bookFromDatabase);
        }
        this.sessionObject.getBasket().clear();
    }
}
