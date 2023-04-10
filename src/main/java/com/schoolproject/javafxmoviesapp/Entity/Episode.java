package com.schoolproject.javafxmoviesapp.Entity;

public class Episode {
    private int id;
    private String name;
    private String link;
    private int filmId;

    public Episode() {
    }

    public Episode(String name, String link, int filmId) {
        this.name = name;
        this.link = link;
        this.filmId = filmId;
    }

    public Episode(int id, String name, String link, int filmId) {
        this.id = id;
        this.name = name;
        this.link = link;
        this.filmId = filmId;
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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getFilmId() {
        return filmId;
    }

    public void setFilmId(int filmId) {
        this.filmId = filmId;
    }
}
