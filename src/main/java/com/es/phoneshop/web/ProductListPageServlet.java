package com.es.phoneshop.web;

import com.es.phoneshop.cart.HttpSessionCartService;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.ProductDao;
import com.es.phoneshop.recentlyViewed.RecentlyViewedService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductListPageServlet extends HttpServlet {

    private ProductDao productDao;
    private RecentlyViewedService recentlyViewedService;

    @Override
    public void init(ServletConfig config) {

        productDao = ArrayListProductDao.getInstance();
        recentlyViewedService = RecentlyViewedService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println();
        System.out.println();
        String query = request.getParameter("query");
        String sort = request.getParameter("sort");
        String order = request.getParameter("order");
        request.setAttribute("products", productDao.findProducts(query, order, sort));
        request.setAttribute("cart", HttpSessionCartService.getInstance().getCart(request));
        request.setAttribute("recentlyViewed", recentlyViewedService.getRecentlyViewedProductList(request));
        request.getRequestDispatcher("/WEB-INF/pages/productList.jsp").forward(request, response);
    }

}
