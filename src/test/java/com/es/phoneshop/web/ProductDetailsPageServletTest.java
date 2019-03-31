package com.es.phoneshop.web;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.recently_viewed.RecentlyViewedList;
import com.es.phoneshop.recently_viewed.RecentlyViewedService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.LinkedList;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class ProductDetailsPageServletTest {
    @Mock
    private ServletConfig config;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private RequestDispatcher requestDispatcher;

    @Mock
    private RecentlyViewedList recentlyViewedList;

    @Mock
    private HttpSession session;

    @InjectMocks
    private ProductDetailsPageServlet servlet;

    private RecentlyViewedService recentlyViewedService = RecentlyViewedService.getInstance();

    private ArrayListProductDao arrayListProductDao = ArrayListProductDao.getInstance();




    @Before
    public void setUp() throws ServletException {
        Currency usd = Currency.getInstance("USD");
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("sessionList")).thenReturn(recentlyViewedList);
        when(recentlyViewedService.getRecentlyViewedProductList(request)).thenReturn(recentlyViewedList);
        when(recentlyViewedList.getRecentlyViewedProducts()).thenReturn(new LinkedList<>());
        arrayListProductDao.save(new Product(1L, "sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg"));
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(request.getRequestURI()).thenReturn("/phoneshop-servlet-api/products/1");
        when(request.getServletPath()).thenReturn("/products");
        servlet.init(config);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        servlet.doGet(request, response);
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoPost() throws ServletException, IOException {
        servlet.doPost(request, response);
        verify(servlet).doGet(request, response);
    }
}