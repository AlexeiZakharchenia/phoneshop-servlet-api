package com.es.phoneshop.cart;

import javax.servlet.http.HttpServletRequest;

public interface CartService {

    Cart getCart(HttpServletRequest request);

    void add(Cart cart, long productId, int quantity) throws OutOfStockException;

    void update(Cart cart, long productId, int quantity) throws OutOfStockException;

    void delete(Cart cart, long productId);
}
