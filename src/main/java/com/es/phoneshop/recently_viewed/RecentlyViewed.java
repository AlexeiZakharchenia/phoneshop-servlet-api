package com.es.phoneshop.recently_viewed;

import javax.servlet.http.HttpServletRequest;


public interface RecentlyViewed {
    public RecentlyViewedList getRecentlyViewedProductList(HttpServletRequest request);

    public void addToRecentlyViewedProductList(RecentlyViewedList recentlyViewedList, long productId);
}
