package com.es.phoneshop.cart;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class Cart implements Serializable {
    private List<CartItem> cartItems = new ArrayList<>();

    private BigDecimal totalPrice = new BigDecimal(0);

    private Integer totalQuantity = 0;

    public Cart() {
    }


    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public Integer getTotalQuantity() {
        return totalQuantity;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    void setTotalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    @Override
    public String toString() {
        return "{ Total quantity of products: " + totalQuantity + ". Total price of products: " + totalPrice + "}";
    }
}
