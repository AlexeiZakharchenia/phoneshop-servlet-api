package com.es.phoneshop.recently_viewed;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.LinkedList;
import java.util.Optional;


public class RecentlyViewedService implements RecentlyViewed {
    private static final String SESSION_RECENTLY_VIEWED_KEY = "sessionList";
    private static volatile RecentlyViewedService instance = null;

    private RecentlyViewedService() {
    }

    public static RecentlyViewedService getInstance() {
        RecentlyViewedService localInstance = instance;
        if (instance == null) {
            synchronized (RecentlyViewedService.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new RecentlyViewedService();
                }

            }

        }
        return instance;
    }

    @Override
    public RecentlyViewedList getRecentlyViewedProductList(HttpServletRequest request) {
        HttpSession session = request.getSession();
        RecentlyViewedList recentlyViewedList = (RecentlyViewedList) session.getAttribute(SESSION_RECENTLY_VIEWED_KEY);
        if (recentlyViewedList == null) {
            recentlyViewedList = new RecentlyViewedList();
            session.setAttribute(SESSION_RECENTLY_VIEWED_KEY, recentlyViewedList);
        }
        return recentlyViewedList;
    }

    @Override
    public void addToRecentlyViewedProductList(RecentlyViewedList recentlyViewedList, long productId) {
        Product product = ArrayListProductDao.getInstance().getProduct(productId);

        LinkedList<Product> productList = recentlyViewedList.getRecentlyViewedProducts();
        Optional<Product> recentlyViewedOptional = productList.stream()
                .filter(product1 -> Long.valueOf(productId).equals(product1.getId())).findAny();

        if (recentlyViewedOptional.isPresent()) {
            productList.remove(product);
            productList.addFirst(product);
        } else {
            productList.addFirst(product);
            if (productList.size() > 3) {
                productList.removeLast();
            }
        }
    }
}
