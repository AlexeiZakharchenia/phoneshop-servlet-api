package com.es.phoneshop.order;

import com.es.phoneshop.cart.Cart;

import java.math.BigDecimal;
import java.util.Objects;

public class Order extends Cart {

    private Long id;
    private String secureId;
    private String name;
    private String address;
    private DeliveryMode deliveryMode;
    private BigDecimal deliveryCost;
    private BigDecimal orderTotal;


    public Order(Long id, String secureId, String name, String address, DeliveryMode deliveryMode, BigDecimal deliveryCost, BigDecimal orderTotal) {
        this.id = id;
        this.secureId = secureId;
        this.name = name;
        this.address = address;
        this.deliveryMode = deliveryMode;
        this.deliveryCost = deliveryCost;
        this.orderTotal = orderTotal;
    }

    public Order() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public DeliveryMode getDeliveryMode() {
        return deliveryMode;
    }

    public void setDeliveryMode(DeliveryMode deliveryMode) {
        this.deliveryMode = deliveryMode;
    }

    public BigDecimal getDeliveryCost() {
        return deliveryCost;
    }

    public void setDeliveryCost(BigDecimal deliveryCost) {
        this.deliveryCost = deliveryCost;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSecureId() {
        return secureId;
    }

    public void setSecureId(String secureId) {
        this.secureId = secureId;
    }

    public BigDecimal getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(BigDecimal orderTotal) {
        this.orderTotal = orderTotal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(name, order.name) &&
                Objects.equals(address, order.address) &&
                deliveryMode == order.deliveryMode &&
                Objects.equals(deliveryCost, order.deliveryCost) &&
                Objects.equals(orderTotal, order.orderTotal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, address, deliveryMode, deliveryCost, orderTotal);
    }
}
