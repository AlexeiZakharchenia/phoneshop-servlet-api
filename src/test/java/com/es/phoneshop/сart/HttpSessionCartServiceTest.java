package com.es.phoneshop.сart;

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

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HttpSessionCartServiceTest {

    @Mock
    Cart cart = new Cart();
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpSession session;
    @InjectMocks
    private CartService httpSessionCartService = HttpSessionCartService.getInstance();

    private ArrayListProductDao productDao = ArrayListProductDao.getInstance();


    @Before
    public void setup() {
        when(request.getSession()).thenReturn(session);
        Currency usd = Currency.getInstance("USD");
        productDao.save(new Product(1L, "sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 5, ""));
    }


    @Test
    public void getCartTest() {
        assertEquals(0, httpSessionCartService.getCart(request).getCartItems().size());
    }

    @Test
    public void add() throws OutOfStockException {
        httpSessionCartService.add(httpSessionCartService.getCart(request), 1L, 1);
        assertEquals(1, httpSessionCartService.getCart(request).getCartItems().size());
    }
}

