package com.es.phoneshop.cart;

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
        productDao.clearAll();
        productDao.save(new Product(1L, "sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 5, ""));
        productDao.save(new Product(2L, "iphone6", "Apple iPhone 6", new BigDecimal(1000), usd, 30, ""));
    }


    @Test
    public void getCartTest() {
        assertEquals(0, httpSessionCartService.getCart(request).getCartItems().size());
    }

    @Test
    public void methodsTest() throws OutOfStockException {
        Cart cart = httpSessionCartService.getCart(request);
        httpSessionCartService.add(cart, 1L, 1);
        assertEquals(1, cart.getCartItems().size());
        httpSessionCartService.update(cart, 1L, 2);
        assertEquals(Integer.valueOf(2), cart.getCartItems().get(0).getQuantity());
        httpSessionCartService.deleteProduct(cart, 1L);
        assertEquals(0, cart.getCartItems().size());
        httpSessionCartService.add(cart, 1L, 1);
        httpSessionCartService.clearCart(cart);
        assertEquals(0, cart.getCartItems().size());
    }

}

