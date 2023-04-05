package com.schoolproject.javafxmoviesapp.DAO.Interface;

public interface EpidodeDAO<T> extends BaseDAO<T>{
    public int countAll();
    public int countByCondition(String condition);
}
