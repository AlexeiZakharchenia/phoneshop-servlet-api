package com.es.phoneshop.order;

import com.es.phoneshop.cart.Cart;

import java.util.List;

public interface OrderService {
    Order createOrder(Cart cart, DeliveryMode deliveryMode);

    List<DeliveryMode> getDeliveryModes();

    void placeOrder(Order order);
}
