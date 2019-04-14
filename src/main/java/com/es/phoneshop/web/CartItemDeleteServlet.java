package com.es.phoneshop.web;

import com.es.phoneshop.cart.Cart;
import com.es.phoneshop.cart.CartService;
import com.es.phoneshop.cart.HttpSessionCartService;
import com.es.phoneshop.util.RequestUtility;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class CartItemDeleteServlet extends HttpServlet {

    private CartService cartService;
    private RequestUtility requestUtility;

    @Override
    public void init() {
        requestUtility = RequestUtility.getInstance();
        cartService = HttpSessionCartService.getInstance();
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        long productId = requestUtility.getProductId(request);
        Cart cart = cartService.getCart(request);
        cartService.deleteProduct(cart, productId);
        response.sendRedirect(request.getContextPath() + "/cart?message=Cart item deleted successfully");
    }
}
