package com.es.phoneshop.util;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.ProductDao;

import javax.servlet.http.HttpServletRequest;

public class IdGetter {
    private static volatile IdGetter instance;
    private ProductDao productDao = ArrayListProductDao.getInstance();

    private IdGetter() {
    }

    public static IdGetter getInstance() {

        IdGetter localInstance = instance;

        if (instance == null) {
            synchronized (ArrayListProductDao.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new IdGetter();
                }

            }

        }
        return instance;
    }


    public Long getProductId(HttpServletRequest request) {
        String uri = request.getRequestURI();

        int index = uri.indexOf(request.getServletPath());
        return Long.parseLong(uri.substring(index + request.getServletPath().length() + 1));
    }


}
