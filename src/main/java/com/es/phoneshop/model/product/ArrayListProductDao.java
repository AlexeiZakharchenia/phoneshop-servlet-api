package com.es.phoneshop.model.product;

import java.util.*;
import java.util.function.Function;
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
    public synchronized List<Product> findProducts(String query) {
        List<Product> foundProducts = getActualProducts().collect(Collectors.toList());
        if (query != null) {
            String[] queries = query.split("\\s");

            return foundProducts.stream()
                    .collect(Collectors.toMap(Function.identity(), product -> Arrays.stream(queries)
                            .filter(word -> product.getDescription().toUpperCase().contains(word)).count()))
                    .entrySet().stream()
                    .filter(entry -> entry.getValue() > 0)
                    .sorted(Comparator.comparing(Map.Entry<Product, Long>::getValue).reversed())
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());
        }
        return foundProducts;
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