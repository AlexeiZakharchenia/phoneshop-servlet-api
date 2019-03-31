package com.es.phoneshop.web;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.ProductDao;
import com.es.phoneshop.model.product.ProductNotFoundException;
import com.es.phoneshop.сart.Cart;
import com.es.phoneshop.сart.CartService;
import com.es.phoneshop.сart.HttpSessionCartService;
import com.es.phoneshop.сart.OutOfStockException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductDetailsPageServlet extends HttpServlet {

    private ProductDao productDao;
    private CartService cartService;

    @Override
    public void init() {
        productDao = ArrayListProductDao.getInstance();
        cartService = HttpSessionCartService.getIntstanse();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {


            long productId = getProductId(request);

            request.setAttribute("products", productDao.getProduct(productId));
            request.setAttribute("cart", cartService.getCart(request));

            request.getRequestDispatcher("/WEB-INF/pages/productDetails.jsp").forward(request, response);
        } catch (ProductNotFoundException | NumberFormatException exception) {
            response.sendError(404, exception.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long productId = getProductId(request);
        Integer quantity;
        try {
            quantity = Integer.valueOf(request.getParameter("quantity"));
        } catch (NumberFormatException exception) {
            request.setAttribute("error", "not a number");
            doGet(request, response);
            return;

        }
        Cart cart = cartService.getCart(request);
        try {
            cartService.add(cart, productId, quantity);
        } catch (OutOfStockException ex) {
            request.setAttribute("error", ex.getMessage());
            doGet(request, response);
            return;
        }

        response.sendRedirect(request.getRequestURI() + "?message=Added successfully&quantity=" + quantity);


    }

    public Long getProductId(HttpServletRequest request) {
        String uri = request.getRequestURI();

        int index = uri.indexOf(request.getServletPath());
        return Long.parseLong(uri.substring(index + request.getServletPath().length() + 1));
    }
}
