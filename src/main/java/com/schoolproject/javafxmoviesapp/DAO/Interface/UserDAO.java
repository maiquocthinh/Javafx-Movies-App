package com.schoolproject.javafxmoviesapp.DAO.Interface;

public interface UserDAO<T> extends BaseDAO<T>{
    public int updateRole(T t);
    public T findByEmail(String email);
    public  int countAll();
    public int countByCondition(String condition);
}
