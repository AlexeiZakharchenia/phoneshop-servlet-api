package com.es.phoneshop.comments;


import java.util.ArrayList;
import java.util.List;

public class ArrayListProductReviewDao implements ProductReviewDao {
    private static volatile ArrayListProductReviewDao instance = null;
    private List<ProductReview> productsReview = new ArrayList<>();

    private ArrayListProductReviewDao() {
    }

    public static ArrayListProductReviewDao getInstance() {
        ArrayListProductReviewDao localInstance = instance;

        if (instance == null) {
            synchronized (ArrayListProductReviewDao.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new ArrayListProductReviewDao();
                }
            }

        }
        return instance;
    }

    @Override
    public void addComment(Long productId, Comment comment) {
        if (productsReview.stream().anyMatch(p -> p.getProductId().equals(productId))) {
            productsReview.stream()
                    .filter(p -> p.getProductId().equals(productId))
                    .findFirst().get().getProductComments().add(comment);
            return;
        }
        ProductReview productReview = new ProductReview();
        productReview.setProductId(productId);
        productReview.getProductComments().add(comment);
        productsReview.add(productReview);
    }

    @Override
    public List<Comment> getComments(Long productId) {
        if (productsReview.stream()
                .anyMatch(p -> p.getProductId().equals(productId))) {
            return productsReview.stream()
                    .filter(p -> p.getProductId().equals(productId))
                    .findFirst().get().getProductComments();
        }
        return new ArrayList<>();
    }
}
