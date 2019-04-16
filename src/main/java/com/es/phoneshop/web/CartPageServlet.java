package com.es.phoneshop.web;

import com.es.phoneshop.cart.Cart;
import com.es.phoneshop.cart.CartService;
import com.es.phoneshop.cart.HttpSessionCartService;
import com.es.phoneshop.cart.OutOfStockException;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.recentlyViewed.RecentlyViewedService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Objects;


public class CartPageServlet extends HttpServlet {

    private CartService cartService;
    private RecentlyViewedService recentlyViewedService;

    @Override
    public void init() {
        cartService = HttpSessionCartService.getInstance();
        recentlyViewedService = RecentlyViewedService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LinkedList<Product> recentlyViewedList = recentlyViewedService.getRecentlyViewedProductList(request);
        request.setAttribute("cart", cartService.getCart(request));
        request.setAttribute("recentlyViewed", recentlyViewedList);
        request.getRequestDispatcher("/WEB-INF/pages/cart.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] productIds = request.getParameterValues("productId");
        String[] quantities = request.getParameterValues("quantity");

        String[] errors = new String[productIds.length + 1];
        Cart cart = cartService.getCart(request);
        for (int i = 0; i < productIds.length; ++i) {
            long productId = Long.valueOf(productIds[i]);
            Integer quantity = parseQuantity(quantities, errors, i);
            if (quantity != null) {
                try {
                    cartService.update(cart, productId, quantity);
                } catch (OutOfStockException | IllegalArgumentException exception) {
                    errors[i] = exception.getMessage();
                }
            }
        }

        boolean hasError = Arrays.stream(errors).anyMatch(Objects::nonNull);
        if (hasError) {
            request.setAttribute("errors", errors);
            doGet(request, response);
        } else {
            response.sendRedirect(request.getRequestURI() + "?message=Update successfully");
        }
    }

    private Integer parseQuantity(String[] quantities, String[] errors, int i) {
        try {
            return Integer.valueOf(quantities[i]);
        } catch (NumberFormatException exception) {
            errors[i] = "Not a number";
        }
        return null;
    }
}
