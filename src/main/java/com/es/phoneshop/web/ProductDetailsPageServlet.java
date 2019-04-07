package com.es.phoneshop.web;

import com.es.phoneshop.cart.Cart;
import com.es.phoneshop.cart.CartService;
import com.es.phoneshop.cart.HttpSessionCartService;
import com.es.phoneshop.cart.OutOfStockException;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import com.es.phoneshop.model.product.ProductNotFoundException;
import com.es.phoneshop.recentlyViewed.RecentlyViewedService;
import com.es.phoneshop.util.IdGetter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;



public class ProductDetailsPageServlet extends HttpServlet {

    private RecentlyViewedService recentlyViewedService;
    private ProductDao productDao;
    private CartService cartService;
    private IdGetter idGetter;

    @Override
    public void init() {
        idGetter = IdGetter.getInstance();
        recentlyViewedService = RecentlyViewedService.getInstance();
        productDao = ArrayListProductDao.getInstance();
        cartService = HttpSessionCartService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {


            long productId = idGetter.getProductId(request);

            LinkedList<Product> recentlyViewedList = recentlyViewedService.getRecentlyViewedProductList(request);


            request.setAttribute("recentlyViewed", recentlyViewedList);
            request.setAttribute("products", productDao.getProduct(productId));
            request.setAttribute("cart", cartService.getCart(request));
            request.getRequestDispatcher("/WEB-INF/pages/productDetails.jsp").forward(request, response);
            recentlyViewedService.addToRecentlyViewedProductList(recentlyViewedList, productId);
        } catch (ProductNotFoundException | NumberFormatException exception) {
            response.sendError(404, exception.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long productId = idGetter.getProductId(request);
        int quantity;
        try {
            quantity = Integer.valueOf(request.getParameter("quantity"));
        } catch (NumberFormatException exception) {
            request.setAttribute("error", "Not a number");
            doGet(request, response);
            return;
        } catch (IllegalArgumentException exception) {
            request.setAttribute("error", "Invalid input");
            doGet(request, response);
            return;
        }
        Cart cart = cartService.getCart(request);
        try {
            cartService.add(cart, productId, quantity);
        } catch (OutOfStockException | IllegalArgumentException ex) {
            request.setAttribute("error", ex.getMessage());
            doGet(request, response);
            return;
        }

        response.sendRedirect(request.getRequestURI() + "?message=Added successfully&quantity=" + quantity);


    }
}
