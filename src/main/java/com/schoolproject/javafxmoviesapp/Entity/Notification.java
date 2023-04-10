package com.schoolproject.javafxmoviesapp.Entity;

import java.util.Date;

public class Notification {
    private int id;
    private String title;
    private String content;
    private boolean read;
    private Date date;
    private int userId;
    private int filmId;

    public Notification() {
    }

    public Notification(String title, String content, boolean read, Date date, int userId, int filmId) {
        this.title = title;
        this.content = content;
        this.read = read;
        this.date = date;
        this.userId = userId;
        this.filmId = filmId;
    }

    public Notification(int id, String title, String content, boolean read, Date date, int userId, int filmId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.read = read;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
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
