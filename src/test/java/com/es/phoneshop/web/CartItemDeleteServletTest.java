package com.es.phoneshop.web;

import com.es.phoneshop.cart.Cart;
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

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Currency;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CartItemDeleteServletTest {

    @Mock
    Cart cart = new Cart();

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private ServletConfig servletConfig;
    @Mock
    private HttpSession session;


    @InjectMocks
    private CartItemDeleteServlet servlet = new CartItemDeleteServlet();

    private CartService httpSessionCartService = HttpSessionCartService.getInstance();

    private ArrayListProductDao productDao = ArrayListProductDao.getInstance();


    @Before
    public void setup() throws OutOfStockException {
        Currency usd = Currency.getInstance("USD");
        productDao.save(new Product(1L, "sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 5, ""));
        when(request.getRequestURI()).thenReturn("/phoneshop-servlet-api/cart/deleteItem/1");
        when(request.getServletPath()).thenReturn("/deleteItem");
        when(request.getSession()).thenReturn(session);
        httpSessionCartService.add(httpSessionCartService.getCart(request), 1L, 1);
    }

    @Test
    public void testDoPost() throws ServletException, IOException {
        servlet.init(servletConfig);

        servlet.doPost(request, response);

        verify(response).sendRedirect(request.getContextPath() + "/cart?message=Cart item deleted successfully");
    }

}