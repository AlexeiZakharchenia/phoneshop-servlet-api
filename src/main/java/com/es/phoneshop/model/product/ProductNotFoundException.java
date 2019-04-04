package com.es.phoneshop.model.product;

public class ProductNotFoundException extends RuntimeException {
    ProductNotFoundException(String message) {
        super(message);
    }
}
