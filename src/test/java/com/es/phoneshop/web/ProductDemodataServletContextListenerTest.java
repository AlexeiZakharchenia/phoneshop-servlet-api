package com.es.phoneshop.web;


import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


public class ProductDemodataServletContextListenerTest {


    @Mock
    private ServletContext servletContext;

    @Mock
    private ServletContextEvent servletContextEvent;

    @InjectMocks
    private ProductDemodataServletContextListener servlet;

    @Before
    public void setUp() {
        when(servletContextEvent.getServletContext()).thenReturn(servletContext);
        when(servletContext.getInitParameter(anyString())).thenReturn("true");
    }


    @Test
    public void testContextInitialized() {
        servlet.contextInitialized(servletContextEvent);
        servlet.contextDestroyed(null);
    }

}