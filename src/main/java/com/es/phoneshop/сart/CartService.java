package com.es.phoneshop.сart;

import javax.servlet.http.HttpServletRequest;

public interface CartService {

    public Cart getCart(HttpServletRequest request);

    public void add(Cart cart, long productId, int quantity) throws OutOfStockException;

}
