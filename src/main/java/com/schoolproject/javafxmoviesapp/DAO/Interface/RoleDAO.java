package com.schoolproject.javafxmoviesapp.DAO.Interface;

public interface RoleDAO<T> extends BaseDAO<T>{
    public int countAll();
    public int countByCondition(String condition);
}
