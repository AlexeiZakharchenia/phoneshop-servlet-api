package com.es.phoneshop.comments;

import java.util.List;

public interface ProductReviewDao {
    void addComment(Long productId, Comment comment);

    List<Comment> getComments(Long productId);
}
