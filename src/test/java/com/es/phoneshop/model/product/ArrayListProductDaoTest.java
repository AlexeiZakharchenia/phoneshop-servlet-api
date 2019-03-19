package com.es.phoneshop.model.product;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.Assert.assertEquals;

public class ArrayListProductDaoTest
{
    private ProductDao productDao;


    @Mock
    private Currency usd;

    @Mock
    private String imageUrl;

    @Before
    public void setup() {
        productDao = new ArrayListProductDao();
        productDao.save(new Product(1L, "sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg"));
        productDao.save(new Product(2L, "sgs2", "Samsung Galaxy S II", new BigDecimal(200), usd, 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg"));
        productDao.save(new Product(3L, "sgs3", "Samsung Galaxy S III", new BigDecimal(300), usd, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg"));
    }

    @Test
    public void FindProducts() {
        assertEquals(2, productDao.findProducts().size());
    }


    @Test
    public void testSave() {
        Product product = new Product(4L, "iphone", "Apple iPhone", new BigDecimal(200), usd, 10, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone.jpg");
        productDao.save(product);
        assertEquals(3, productDao.findProducts().size());

    }


    @Test
    public void testDelete() {
        productDao.delete(2L);
        assertEquals(2, productDao.findProducts().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetProduct() {
        productDao.getProduct(5L);
    }


}