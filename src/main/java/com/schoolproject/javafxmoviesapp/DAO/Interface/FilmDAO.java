package com.schoolproject.javafxmoviesapp.DAO.Interface;

public interface FilmDAO<T> extends BaseDAO<T> {
    public int updateView(T t);
    public int count();
    
}
