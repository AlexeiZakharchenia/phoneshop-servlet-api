package com.es.phoneshop.web;

import com.es.phoneshop.cart.CartService;
import com.es.phoneshop.cart.HttpSessionCartService;
import com.es.phoneshop.cart.OutOfStockException;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
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

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class CartPageServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private ServletConfig servletConfig;
    @Mock
    private HttpSession session;

    @InjectMocks
    private CartPageServlet servlet = new CartPageServlet();

    private ArrayListProductDao productDao = ArrayListProductDao.getInstance();

    private CartService cartService = HttpSessionCartService.getInstance();

    @Before
    public void setup() throws OutOfStockException {
        when(request.getSession()).thenReturn(session);
        Currency usd = Currency.getInstance("USD");
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        productDao.clearAll();
        productDao.save(new Product(1L, "sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 5, ""));
        cartService.add(cartService.getCart(request), 1L, 1);
        String[] quantity = {"1"};
        String[] productId = {"1"};
        when(request.getParameterValues("quantity")).thenReturn(quantity);
        when(request.getParameterValues("productId")).thenReturn(productId);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        servlet.init(servletConfig);

        servlet.doGet(request, response);

        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoPost() throws ServletException, IOException {
        servlet.init(servletConfig);

        servlet.doPost(request, response);
        verify(response).sendRedirect(request.getRequestURI() + "?message=Update successfully");
    }

}