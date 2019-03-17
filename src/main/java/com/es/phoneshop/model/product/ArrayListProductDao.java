package com.es.phoneshop.model.product;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {

    private List<Product> products = new ArrayList<>();


    @Override
    public synchronized Product getProduct(Long id) {
        return products.stream().filter(p -> p.getId().equals(id) &&
                p.getPrice() != null &&
                p.getStock() > 0).findFirst().
                orElseThrow(() -> new IllegalArgumentException("Product with that id not exists"));
    }

    @Override
    public synchronized List<Product> findProducts() {
        return products.stream().filter(p -> p.getPrice() != null &&
                p.getStock() > 0)
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
        products.stream().filter(p -> (p.getId().equals(id)))
                .findFirst().map(i -> products.remove(i))
                .orElseThrow(() -> new IllegalArgumentException("Rroduct woth that id not existing"));
    }

}