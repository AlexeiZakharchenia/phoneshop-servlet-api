package com.es.phoneshop.model.product;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ArrayListProductDao implements ProductDao {

    private List<Product> products = new ArrayList<>();


    private Stream<Product> getActualProducts() {
        return products.stream().filter(p -> p.getPrice() != null &&
                p.getStock() > 0);
    }


    @Override
    public synchronized Product getProduct(Long id) {
        return getActualProducts().filter(p -> p.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Product with that id not exists"));
    }

    @Override
    public synchronized List<Product> findProducts() {
        return getActualProducts()
                .collect(Collectors.toList());
    }

    @Override
    public synchronized void save(Product product) {
        if (products.stream()
                .anyMatch(p -> p.getId().equals(product.getId()))) {
            throw new IllegalArgumentException("Product with that id already exists");
        } else {
            products.add(product);
        }
    }

    @Override
    public synchronized void delete(Long id) {
        if (!products.removeIf(p -> p.getId().equals(id))) {
            throw new IllegalArgumentException("Product with that id not exists");
        }
    }

}