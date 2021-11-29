package com.it.services;

public interface IBasketService {

    void addToBasket(int id);

    double calculateBill();

    void removeBookFromBasket(int id);

    void confirmOrder();
}
