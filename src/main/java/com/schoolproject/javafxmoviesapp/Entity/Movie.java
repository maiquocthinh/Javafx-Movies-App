package com.schoolproject.javafxmoviesapp.Entity;

public class Movie {
    private String name;
    private String ImageSrc;
    private String publishing;
//    Nhà xuất bản

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageSrc() {
        return ImageSrc;
    }

    public void setImageSrc(String imageSrc) {
        ImageSrc = imageSrc;
    }

    public String getPublishing() {
        return publishing;
    }

    public void setPublishing(String publishing) {
        this.publishing = publishing;
    }
}
