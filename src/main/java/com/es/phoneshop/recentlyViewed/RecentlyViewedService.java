package com.es.phoneshop.recentlyViewed;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.LinkedList;
import java.util.Optional;


public class RecentlyViewedService implements RecentlyViewed {
    public static final String SESSION_RECENTLY_VIEWED_KEY = "sessionList";
    private static volatile RecentlyViewedService instance = null;
    private ProductDao productDao = ArrayListProductDao.getInstance();

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
    public LinkedList<Product> getRecentlyViewedProductList(HttpServletRequest request) {
        HttpSession session = request.getSession();
        LinkedList<Product> recentlyViewedList = (LinkedList<Product>) session.getAttribute(SESSION_RECENTLY_VIEWED_KEY);
        if (recentlyViewedList == null) {
            recentlyViewedList = new LinkedList<>();
            session.setAttribute(SESSION_RECENTLY_VIEWED_KEY, recentlyViewedList);
        }
        return recentlyViewedList;
    }

    @Override
    public void addToRecentlyViewedProductList(LinkedList<Product> recentlyViewedList, long productId) {
        Product product = productDao.getProduct(productId);

        Optional<Product> recentlyViewedOptional = recentlyViewedList.stream()
                .filter(product1 -> Long.valueOf(productId).equals(product1.getId())).findAny();

        if (recentlyViewedOptional.isPresent()) {
            recentlyViewedList.remove(product);
            recentlyViewedList.addFirst(product);
        } else {
            recentlyViewedList.addFirst(product);
            if (recentlyViewedList.size() > 3) {
                recentlyViewedList.removeLast();
            }
        }
    }
}
