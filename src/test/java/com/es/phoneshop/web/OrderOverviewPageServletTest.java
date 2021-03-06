package com.es.phoneshop.web;

import com.es.phoneshop.order.ArrayListOrderDao;
import com.es.phoneshop.order.Order;
import com.es.phoneshop.order.OrderDao;
import com.es.phoneshop.order.OrderNotFoundException;
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
import java.io.IOException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderOverviewPageServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private ServletConfig servletConfig;

    @InjectMocks
    private OrderOverviewPageServlet servlet;

    private OrderDao orderDao;


    @Before
    public void setup() throws OrderNotFoundException {
        orderDao = ArrayListOrderDao.getInstance();
        Order order = new Order();
        orderDao.save(order);
        order.setSecureId("secureId");
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(request.getPathInfo()).thenReturn("secureId");
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        servlet.init(servletConfig);

        servlet.doGet(request, response);

        verify(requestDispatcher).forward(request, response);
    }


}