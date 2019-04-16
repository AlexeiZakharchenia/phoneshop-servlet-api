package com.es.phoneshop.order;

import com.es.phoneshop.cart.Cart;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class OrderServiceImplTest {
    @Mock
    private Cart cart;


    @InjectMocks
    private OrderServiceImpl service;

    @Before
    public void setup() {
        when(cart.getCartItems()).thenReturn(new ArrayList<>());
        when(cart.getTotalPrice()).thenReturn(BigDecimal.ZERO);
        when(cart.getTotalQuantity()).thenReturn(0);
    }


    @Test
    public void createOrderTest() {
        assertNotNull(service.createOrder(cart, DeliveryMode.COURIER));
    }

    @Test
    public void getDeliveryModesTest() {
        assertNotNull(service.getDeliveryModes());
    }

    @Test
    public void getPaymentMethods() {
        assertNotNull(service.getPaymentMethods());
    }

    @Test
    public void placeOrderTest() {
        service.placeOrder(new Order());
        assertEquals(1, ArrayListOrderDao.getInstance().getOrders().size());
    }
}