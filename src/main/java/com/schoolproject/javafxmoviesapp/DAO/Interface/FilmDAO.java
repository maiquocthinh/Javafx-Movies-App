package com.schoolproject.javafxmoviesapp.DAO.Interface;

import java.util.List;

public interface FilmDAO<T> extends BaseDAO<T> {
    public int updateView(T t);
    public int getRating(T t);
    public int updateRating(T t);
    public int countAll();
    public int countByCondition(String condition);
    public List<T> selectTopViewByDay(int amount);
    public List<T> selectTopViewByMonth(int amount);
    public List<T> selectTopViewByYear(int amount);
    public int getTotalFollow(int filmId);
    public boolean isFollowed(int filmId, int userId);
    public int followFilm(int filmId, int userId);
    public int unfollowFilm(int filmId, int userId);

}
