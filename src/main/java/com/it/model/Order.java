package com.it.model;

import java.util.List;

public class Order {
    int id;
    User user;
    List<OrderPosition> positions;
    double price;
    Status status;

    public enum Status {
        ORDERED,
        ACCEPTED,
        SENT,
        FINISHED
    }
}
