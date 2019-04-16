package com.es.phoneshop.web;

import com.es.phoneshop.cart.Cart;
import com.es.phoneshop.cart.CartService;
import com.es.phoneshop.cart.HttpSessionCartService;
import com.es.phoneshop.order.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class CheckoutPageServlet extends HttpServlet {

    private CartService cartService;
    private OrderService orderService;

    @Override
    public void init() {
        cartService = HttpSessionCartService.getInstance();
        orderService = OrderServiceImpl.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = cartService.getCart(request);
        if (cart.getCartItems().size() == 0) {
            response.sendRedirect(request.getContextPath() + "/cart?errorMessage=You have no products in cart");
            return;
        }
        request.setAttribute("cart", cart);
        request.setAttribute("deliveryModes", orderService.getDeliveryModes());
        request.setAttribute("paymentMethods", orderService.getPaymentMethods());
        request.getRequestDispatcher("/WEB-INF/pages/checkout.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = cartService.getCart(request);
        String deliveryModeString = request.getParameter("deliveryMode");
        boolean hasError = false;
        DeliveryMode deliveryMode = null;
        if (deliveryModeString != null) {
            int index = deliveryModeString.indexOf('(');
            deliveryModeString = deliveryModeString.substring(0, index);
            deliveryMode = DeliveryMode.valueOf(deliveryModeString);
        } else {
            hasError = true;
            request.setAttribute("deliveryModeError", "Unknown delivery mode");
        }

        String name = request.getParameter("name");
        if (name == null || name.isEmpty()) {
            hasError = true;
            request.setAttribute("nameError", "Name is required");
        }
        String address = request.getParameter("address");
        if (address == null || address.isEmpty()) {
            hasError = true;
            request.setAttribute("addressError", "Address is required");
        }
        String dateString = request.getParameter("deliveryDate");
        Date date = null;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            date = simpleDateFormat.parse(dateString);
        } catch (ParseException ex) {
            hasError = true;
            request.setAttribute("deliveryDateError", "Invalid date");
        }
        PaymentMethod paymentMethod = null;
        String paymentMethodString = request.getParameter("paymentMethod");
        if (paymentMethodString != null) {
            paymentMethod = PaymentMethod.valueOf(paymentMethodString);
        } else {
            hasError = true;
            request.setAttribute("paymentMethodError", "Unknown payment method");
        }
        if (hasError) {
            doGet(request, response);
            return;
        }


        Order order = orderService.createOrder(cart, deliveryMode);
        order.setName(name);
        order.setAddress(address);
        order.setDeliveryMode(deliveryMode);
        order.setDeliveryCost((deliveryMode.getDeliveryCost()));
        order.setOrderTotal(order.getDeliveryCost().add(order.getTotalPrice()));
        order.setPaymentMethod(paymentMethod);
        order.setDate(date);
        orderService.placeOrder(order);
        cartService.clearCart(cart, request);
        response.sendRedirect(request.getRequestURI() + "/orderOverview/" + order.getSecureId());
    }
}