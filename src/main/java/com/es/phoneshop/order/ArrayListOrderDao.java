package com.es.phoneshop.order;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ArrayListOrderDao implements OrderDao {
    private static volatile ArrayListOrderDao instance = null;

    private List<Order> orders = new ArrayList<>();
    private AtomicLong orderId = new AtomicLong();

    private ArrayListOrderDao() {
    }

    public static ArrayListOrderDao getInstance() {
        ArrayListOrderDao localInstance = instance;

        if (instance == null) {
            synchronized (ArrayListOrderDao.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new ArrayListOrderDao();
                }

            }

        }
        return instance;
    }

    @Override
    public void save(Order order) {
        order.setId(orderId.incrementAndGet());
        orders.add(order);
        order.setSecureId(UUID.randomUUID().toString());

    }

    @Override
    public Order getBySecureId(String secureId) throws OrderNotFoundException {
        return orders.stream()
                .filter(order -> secureId.equals(order.getSecureId()))
                .findFirst()
                .orElseThrow(() -> new OrderNotFoundException("Order with  secure id: " + secureId + " not exists"));
    }
}
