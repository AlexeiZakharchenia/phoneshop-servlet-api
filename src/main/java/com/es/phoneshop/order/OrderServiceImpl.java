package com.es.phoneshop.order;

import com.es.phoneshop.cart.Cart;
import com.es.phoneshop.cart.CartItem;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class OrderServiceImpl implements OrderService {
    public static final BigDecimal COURIER_COST = new BigDecimal(5);
    public static final BigDecimal STOREPICKUP_COST = BigDecimal.ZERO;
    private static volatile OrderServiceImpl instance = null;
    private OrderDao orderDao = ArrayListOrderDao.getInstance();

    private OrderServiceImpl() {
    }

    public static OrderServiceImpl getInstance() {
        OrderServiceImpl localInstance = instance;

        if (instance == null) {
            synchronized (OrderServiceImpl.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new OrderServiceImpl();
                }

            }

        }
        return instance;
    }

    @Override
    public Order createOrder(Cart cart, DeliveryMode deliveryMode) {
        Order order = new Order();
        order.setCartItems(cart.getCartItems().stream().map(CartItem::new).collect(Collectors.toList()));
        order.setTotalPrice(cart.getTotalPrice());
        order.setTotalQuantity(cart.getTotalQuantity());
        if (deliveryMode == DeliveryMode.COURIER) {
            order.setDeliveryMode(deliveryMode);
            order.setDeliveryCost(deliveryMode.getDeliveryCost());
        } else {
            order.setDeliveryMode(deliveryMode);
            order.setDeliveryCost(deliveryMode.getDeliveryCost());
        }
        order.setOrderTotal(order.getDeliveryCost().add(order.getTotalPrice()));
        return order;
    }

    @Override
    public List<DeliveryMode> getDeliveryModes() {
        return Arrays.asList(DeliveryMode.values());
    }

    @Override
    public void placeOrder(Order order) {
        orderDao.save(order);
    }

    @Override
    public List<PaymentMethod> getPaymentMethods() {
        return Arrays.asList(PaymentMethod.values());
    }
}
