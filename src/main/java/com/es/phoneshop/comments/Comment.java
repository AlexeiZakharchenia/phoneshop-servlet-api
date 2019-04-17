package com.es.phoneshop.comments;

public class Comment {
    String name;
    Integer rating;
    String commentText;


    public Comment() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "name='" + name + '\'' +
                ", rating=" + rating +
                ", commentText='" + commentText + '\'' +
                '}';
    }
}
