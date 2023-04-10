package com.schoolproject.javafxmoviesapp.Entity;

import java.util.Date;

public class CollectionItem {
    private int userId;
    private int filmId;
    private Date date;

    public CollectionItem() {
    }

    public CollectionItem(int userId, int filmId, Date date) {
        this.userId = userId;
        this.filmId = filmId;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
