package com.es.phoneshop.recently_viewed;

import com.es.phoneshop.model.product.Product;

import java.util.LinkedList;

public class RecentlyViewedList {
    private LinkedList<Product> recentlyViewedProducts = new LinkedList<>();

    public LinkedList<Product> getRecentlyViewedProducts() {
        return recentlyViewedProducts;
    }
}
