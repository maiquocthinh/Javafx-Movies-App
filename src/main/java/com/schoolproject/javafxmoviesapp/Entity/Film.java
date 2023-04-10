package com.schoolproject.javafxmoviesapp.Entity;

public class Film {
    private int id;
    private String name;
    private String poster;
    private String backdrop;
    private String trailer;
    private String content;
    private int release;
    private String type;
    private String status;
    private String runtime;
    private String quality;
    private float rating;
    private int viewed;
    private boolean popular;

    public Film() {
    }

    public Film(String name, String poster, String backdrop, String trailer, String content, int release, String type, String status, String runtime, String quality, float rating, int viewed,boolean popular) {
        this.name = name;
        this.poster = poster;
        this.backdrop = backdrop;
        this.trailer = trailer;
        this.content = content;
        this.release = release;
        this.type = type;
        this.status = status;
        this.runtime = runtime;
        this.quality = quality;
        this.rating = rating;
        this.viewed = viewed;
        this.popular = popular;
    }

    public Film(int id, String name, String poster, String backdrop, String trailer, String content, int release, String type, String status, String runtime, String quality, float rating, int viewed, boolean popular) {
        this.id = id;
        this.name = name;
        this.poster = poster;
        this.backdrop = backdrop;
        this.trailer = trailer;
        this.content = content;
        this.release = release;
        this.type = type;
        this.status = status;
        this.runtime = runtime;
        this.quality = quality;
        this.rating = rating;
        this.viewed = viewed;
        this.popular = popular;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getBackdrop() {
        return backdrop;
    }

    public void setBackdrop(String backdrop) {
        this.backdrop = backdrop;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getRelease() {
        return release;
    }

    public void setRelease(int release) {
        this.release = release;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getViewed() {
        return viewed;
    }

    public void setViewed(int viewed) {
        this.viewed = viewed;
    }

    public boolean isPopular() {
        return popular;
    }

    public void setPopular(boolean popular) {
        this.popular = popular;
    }
}
