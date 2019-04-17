package com.es.phoneshop.web;


import com.es.phoneshop.comments.ArrayListProductReviewDao;
import com.es.phoneshop.comments.Comment;
import com.es.phoneshop.comments.ProductReviewDao;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.ProductNotFoundException;
import com.es.phoneshop.util.RequestUtility;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CreateProductCommentServlet extends HttpServlet {

    ProductReviewDao productReviewDao;

    @Override
    public void init() {
        productReviewDao = ArrayListProductReviewDao.getInstance();
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long productId = null;
        try {
            productId = RequestUtility.getInstance().getProductId(request);
        } catch (NumberFormatException ex) {
            response.sendError(404);
            return;
        }
        try {
            ArrayListProductDao.getInstance().getProduct(productId);
        } catch (ProductNotFoundException ex) {
            response.sendError(404, ex.getMessage());
            return;
        }

        String name = request.getParameter("name");
        if (name == null || name.isEmpty()) {
            request.setAttribute("nameError", "Name is required");
            render(request, response, productId);
            return;
        }
        String ratingString = request.getParameter("rating");
        Integer rating = null;
        if (ratingString == null || ratingString.isEmpty()) {
            request.setAttribute("ratingError", "Rating is required");
            render(request, response, productId);
            return;
        }
        try {
            rating = Integer.parseInt(ratingString);
        } catch (NumberFormatException exception) {
            request.setAttribute("ratingError", "Not a number");
            render(request, response, productId);
            return;

        }
        if (rating > 5 || rating <= 0) {
            request.setAttribute("ratingError", "Not a number");
            render(request, response, productId);
            return;
        }
        String commentText = request.getParameter("commentText");
        if (commentText == null) {
            commentText = "";
        }
        Comment comment = new Comment();
        comment.setRating(rating);
        comment.setName(name);
        comment.setCommentText(commentText);
        productReviewDao.addComment(productId, comment);
        response.sendRedirect(request.getContextPath() + "/products/" + productId + "?commentMessage=Cart item deleted successfully");
    }

    private void render(HttpServletRequest request, HttpServletResponse response, Long productId) throws IOException {
        request.setAttribute("productComments", productReviewDao.getComments(productId));
        response.sendRedirect(request.getContextPath() + "/products/" + productId + "?commentMessage=Incorrect comment");
    }
}
