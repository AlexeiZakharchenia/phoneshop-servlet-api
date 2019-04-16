package com.es.phoneshop.web;

import com.es.phoneshop.cart.Cart;
import com.es.phoneshop.cart.CartItem;
import com.es.phoneshop.cart.HttpSessionCartService;
import com.es.phoneshop.cart.OutOfStockException;
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

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class CheckoutPageServletTest {
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
    private CheckoutPageServlet servlet;


    @Before
    public void setup() throws OutOfStockException {
        when(request.getSession()).thenReturn(session);
        Cart cart = new Cart();
        cart.getCartItems().add(new CartItem());
        when(session.getAttribute(HttpSessionCartService.SESSION_CART_KEY)).thenReturn(cart);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(request.getParameter("name")).thenReturn("COURIER");
        when(request.getParameter("deliveryDate")).thenReturn("2015-12-12");
        when(request.getParameter("deliveryMode")).thenReturn("COURIER($2)");
        when(request.getParameter("address")).thenReturn("address");
        when(request.getParameter("paymentMethod")).thenReturn("MONEY");
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
        verify(response).sendRedirect(anyString());
    }

}