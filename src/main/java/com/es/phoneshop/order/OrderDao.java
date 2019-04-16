package com.es.phoneshop.order;


public interface OrderDao {

    void save(Order order);

    Order getBySecureId(String secureId) throws OrderNotFoundException;

}
