package com.es.phoneshop.comments;

import java.util.ArrayList;

public class ProductReview {
    Long productId;
    ArrayList<Comment> productComments = new ArrayList<>();

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public ArrayList<Comment> getProductComments() {
        return productComments;
    }

    public void setProductComments(ArrayList<Comment> productComments) {
        this.productComments = productComments;
    }
}
