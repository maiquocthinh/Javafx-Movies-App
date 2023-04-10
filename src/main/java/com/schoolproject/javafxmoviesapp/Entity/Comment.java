package com.schoolproject.javafxmoviesapp.Entity;

import java.util.Date;

public class Comment {
    private int id;
    private String content;
    private Date date;
    private int userId;
    private int filmId;

    public Comment(String content, Date date, int userId, int filmId) {
        this.content = content;
        this.date = date;
        this.userId = userId;
        this.filmId = filmId;
    }

    public Comment(int id, String content, Date date, int userId, int filmId) {
        this.id = id;
        this.content = content;
        this.date = date;
        this.userId = userId;
        this.filmId = filmId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getFilmId() {
        return filmId;
    }

    public void setFilmId(int filmId) {
        this.filmId = filmId;
    }
}
