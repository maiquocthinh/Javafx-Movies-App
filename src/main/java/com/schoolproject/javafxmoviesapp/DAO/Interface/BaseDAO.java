package com.schoolproject.javafxmoviesapp.DAO.Interface;

import java.util.List;

public interface BaseDAO<T> {
    public int insert(T t);
    public int update(T t);
    public int delete(T t);
    public List<T> selectAll();
    public T findById(int id);
    public List<T> selectByCondition(String condition);

}
