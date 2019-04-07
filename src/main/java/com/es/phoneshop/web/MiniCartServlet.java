package com.es.phoneshop.web;

import com.es.phoneshop.cart.CartService;
import com.es.phoneshop.cart.HttpSessionCartService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class MiniCartServlet extends HttpServlet {

    private CartService cartService;

    @Override
    public void init() {
        cartService = HttpSessionCartService.getInstance();
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setAttribute("cart", cartService.getCart(request));
        request.getRequestDispatcher("/WEB-INF/fragments/minicart.jsp").include(request, response);
    }
}
