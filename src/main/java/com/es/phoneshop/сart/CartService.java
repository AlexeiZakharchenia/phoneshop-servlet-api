package com.es.phoneshop.сart;

import javax.servlet.http.HttpServletRequest;

public interface CartService {

    Cart getCart(HttpServletRequest request);

    void add(Cart cart, long productId, int quantity) throws OutOfStockException;

}
