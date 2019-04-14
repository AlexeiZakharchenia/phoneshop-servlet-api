package com.es.phoneshop.cart;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


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

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setTotalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    @Override
    public String toString() {
        return "{ Total quantity of products: " + totalQuantity + ". Total price of products: " + totalPrice + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cart cart = (Cart) o;
        return Objects.equals(cartItems, cart.cartItems) &&
                Objects.equals(totalPrice, cart.totalPrice) &&
                Objects.equals(totalQuantity, cart.totalQuantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cartItems, totalPrice, totalQuantity);
    }
}
