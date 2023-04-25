package com.schoolproject.javafxmoviesapp.DAO.Interface;

import java.util.List;

public interface CountryDAO<T> extends BaseDAO<T>{
    public int countAll();
    public int countByCondition(String condition);
    public List<T> selectByFilmId(int filmId);
}
