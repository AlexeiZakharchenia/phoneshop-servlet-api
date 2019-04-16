package com.es.phoneshop.order;

import com.es.phoneshop.cart.Cart;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

public class Order extends Cart {

    private Long id;
    private String secureId;
    private String name;
    private String address;
    private DeliveryMode deliveryMode;
    private BigDecimal deliveryCost;
    private BigDecimal orderTotal;
    private Date date;
    private PaymentMethod paymentMethod;


    public Order() {
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
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
        if (!super.equals(o)) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) &&
                Objects.equals(secureId, order.secureId) &&
                Objects.equals(name, order.name) &&
                Objects.equals(address, order.address) &&
                deliveryMode == order.deliveryMode &&
                Objects.equals(deliveryCost, order.deliveryCost) &&
                Objects.equals(orderTotal, order.orderTotal) &&
                Objects.equals(date, order.date) &&
                paymentMethod == order.paymentMethod;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, secureId, name, address, deliveryMode, deliveryCost, orderTotal, date, paymentMethod);
    }
}
