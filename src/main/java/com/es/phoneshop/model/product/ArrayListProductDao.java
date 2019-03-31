package com.es.phoneshop.model.product;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ArrayListProductDao implements ProductDao {

    private static volatile ArrayListProductDao instance = null;



    private ArrayListProductDao() {
    }

    public static ArrayListProductDao getInstance() {

        ArrayListProductDao localInstance = instance;

        if (instance == null) {
            synchronized (ArrayListProductDao.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new ArrayListProductDao();
                }

            }

        }
        return instance;

    }

    private List<Product> products = new ArrayList<>();

    private Stream<Product> getActualProducts() {
        return products.stream().filter(p -> p.getPrice() != null &&
                p.getStock() > 0);
    }

    @Override
    public synchronized Product getProduct(Long id) {
        return getActualProducts().filter(p -> p.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException("Product with  id: " + id.toString() + " not exists"));
    }

    @Override
    public synchronized List<Product> findProducts(String query, String order, String sort) {
        List<Product> foundProducts = getActualProducts().collect(Collectors.toList());
        if (query != null) {
            String[] queries = query.toUpperCase().split("\\s");

            foundProducts = foundProducts.stream()
                    .collect(Collectors.toMap(Function.identity(), product -> Arrays.stream(queries)
                            .filter(word -> product.getDescription().toUpperCase().contains(word)).count()))
                    .entrySet().stream()
                    .filter(entry -> entry.getValue() > 0)
                    .sorted(Comparator.comparing(Map.Entry<Product, Long>::getValue).reversed())
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());
        }
        if (sort != null)
            return sortProducts(foundProducts, order, sort);
        return foundProducts;
    }

    @Override
    public synchronized void save(Product product) {
        if (products.stream()
                .anyMatch(p -> p.getId().equals(product.getId()))) {
            throw new ProductNotFoundException("Product with id: " + product.getId().toString() + " already exists");
        } else {
            products.add(product);
        }
    }

    @Override
    public synchronized void delete(Long id) {
        if (!products.removeIf(p -> p.getId().equals(id))) {
            throw new ProductNotFoundException("Product with  id: " + id.toString() + " not exists");
        }
    }


    private List<Product> sortProducts(List<Product> productList, String order, String sort) {
        Comparator<Product> comparator = (o1, o2) -> 0;
        if (sort.equals("description")) {
            comparator = Comparator.comparing(Product::getDescription);
        }

        if (sort.equals("price")) {
            comparator = Comparator.comparing(Product::getPrice);
        }

        if (order.equals("desc")) {
            comparator = comparator.reversed();
        }

        return productList.stream().sorted(comparator).collect(Collectors.toList());
    }


    public void clearAll() {
        products.clear();
    }

}