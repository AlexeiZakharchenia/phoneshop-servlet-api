package com.es.phoneshop.model.product;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
    }

    @Test
    public void testFindProductsNoResults() {
        assertTrue(productDao.findProducts().isEmpty());
    }


    @Test
    public void testSaveAndDelete() {
        Product product = new Product(1L, "sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, imageUrl);
        productDao.save(product);
        assertEquals(1, productDao.findProducts().size());
        productDao.delete(1L);
        assertEquals(0, productDao.findProducts().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetProduct() {
        productDao.getProduct(1L);
    }


}