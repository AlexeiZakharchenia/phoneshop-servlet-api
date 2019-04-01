package com.es.phoneshop.recentlyViewed;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.LinkedList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class RecentlyViewedServiceTest {


    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession session;

    @InjectMocks
    private RecentlyViewedService recentlyViewedService = RecentlyViewedService.getInstance();

    private LinkedList<Product> productLinkedList;
    private ArrayListProductDao productDao = ArrayListProductDao.getInstance();

    @Before
    public void setup() {
        when(request.getSession()).thenReturn(session);
        Currency usd = Currency.getInstance("USD");
        productDao.clearAll();
        productDao.save(new Product(1L, "sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 5, ""));
        productLinkedList = recentlyViewedService.getRecentlyViewedProductList(request);


    }

    @Test
    public void addToRecentlyViewedProductListTest() {
        recentlyViewedService.addToRecentlyViewedProductList(productLinkedList, 1L);
        assertEquals(1, productLinkedList.size());
    }
}