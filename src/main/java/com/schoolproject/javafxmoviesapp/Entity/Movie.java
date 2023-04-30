package com.schoolproject.javafxmoviesapp.Entity;

public class Movie {
    private String name;
    private String image;
    private float rating;

    public Movie(String name, String image, float rating, int totalComment, int totalView) {
        this.name = name;
        this.image = image;
        this.rating = rating;
        this.totalComment = totalComment;
        this.totalView = totalView;
    }

    private int totalComment;
    private int totalView;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getTotalComment() {
        return totalComment;
    }

    public void setTotalComment(int totalComment) {
        this.totalComment = totalComment;
    }

    public int getTotalView() {
        return totalView;
    }

    public void setTotalView(int totalView) {
        this.totalView = totalView;
    }
}
