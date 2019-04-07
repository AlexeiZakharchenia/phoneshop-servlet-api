package com.es.phoneshop.cart;

public class OutOfStockException extends Exception {
    OutOfStockException(String message) {
        super(message);
    }
}
