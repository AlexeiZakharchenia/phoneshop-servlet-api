package com.es.phoneshop.сart;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<CartItem> cartItems = new ArrayList<>();

    private BigDecimal totalPrice = new BigDecimal(0);

    private Integer totalQuantity = 0;

    Cart() {
    }

    List<CartItem> getCartItems() {
        return cartItems;
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
