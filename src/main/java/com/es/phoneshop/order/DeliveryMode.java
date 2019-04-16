package com.es.phoneshop.order;


import java.math.BigDecimal;

public enum DeliveryMode {
    COURIER(BigDecimal.valueOf(5)), STOREPICKUP(BigDecimal.valueOf(0));

    private BigDecimal deliveryCost;

    DeliveryMode(BigDecimal deliveryCost) {
        this.deliveryCost = deliveryCost;
    }

    public BigDecimal getDeliveryCost() {
        return deliveryCost;
    }
}
