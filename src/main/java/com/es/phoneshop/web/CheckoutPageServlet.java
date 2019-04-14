package com.es.phoneshop.web;

import com.es.phoneshop.cart.Cart;
import com.es.phoneshop.cart.CartService;
import com.es.phoneshop.cart.HttpSessionCartService;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.ProductDao;
import com.es.phoneshop.order.DeliveryMode;
import com.es.phoneshop.order.Order;
import com.es.phoneshop.order.OrderService;
import com.es.phoneshop.order.OrderServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class CheckoutPageServlet extends HttpServlet {

    private CartService cartService;
    private ProductDao productDao;
    private OrderService orderService;

    @Override
    public void init() {
        productDao = ArrayListProductDao.getInstance();
        cartService = HttpSessionCartService.getInstance();
        orderService = OrderServiceImpl.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = cartService.getCart(request);
        String deliveryModeString = request.getParameter("deliveryMode");
        DeliveryMode deliveryMode = null;
        if (deliveryModeString != null) {
            deliveryMode = DeliveryMode.valueOf(deliveryModeString);
        }
        request.setAttribute("order", orderService.createOrder(cart, deliveryMode));
        request.setAttribute("deliveryModes", orderService.getDeliveryModes());
        request.getRequestDispatcher("/WEB-INF/pages/checkout.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = cartService.getCart(request);
        String deliveryModeString = request.getParameter("deliveryMode");
        DeliveryMode deliveryMode = null;
        if (deliveryModeString != null) {
            deliveryMode = DeliveryMode.valueOf(deliveryModeString);
        }
        Order order = orderService.createOrder(cart, deliveryMode);
        boolean hasError = false;
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
        if (hasError) {
            renderCheckoutPage(request, response, order);
            return;
        }
        order.setName(name);
        order.setAddress(address);
        order.setDeliveryMode(deliveryMode);
        order.setDeliveryCost((deliveryMode == DeliveryMode.COURIER) ? OrderServiceImpl.COURIER_COST : OrderServiceImpl.STOREPICKUP_COST);
        order.setOrderTotal(order.getDeliveryCost().add(order.getTotalPrice()));
        orderService.placeOrder(order);
        cartService.clearCart(cart);
        response.sendRedirect(request.getContextPath() + "/orderOverview/" + order.getSecureId());
    }

    private void renderCheckoutPage(HttpServletRequest request, HttpServletResponse response, Order order)
            throws ServletException, IOException {
        request.setAttribute("order", order);
        request.setAttribute("deliveryModes", orderService.getDeliveryModes());
        request.getRequestDispatcher("/WEB-INF/pages/checkout.jsp").forward(request, response);
    }
}
