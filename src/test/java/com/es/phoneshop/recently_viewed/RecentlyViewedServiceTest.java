package com.es.phoneshop.recently_viewed;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.when;

public class RecentlyViewedServiceTest {


    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession session;

    @InjectMocks
    private RecentlyViewedService recentlyViewedService = RecentlyViewedService.getInstance();

    @Before
    public void setup() {
        when(request.getSession()).thenReturn(session);

    }

    @Test
    public void getRecentlyViewedProductListTest() {
        recentlyViewedService.getRecentlyViewedProductList(request);
    }
}