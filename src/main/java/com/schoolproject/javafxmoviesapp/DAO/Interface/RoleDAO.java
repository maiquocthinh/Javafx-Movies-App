package com.schoolproject.javafxmoviesapp.DAO.Interface;

import com.schoolproject.javafxmoviesapp.Entity.User;

public interface RoleDAO<T> extends BaseDAO<T>{
    public T findByUser(User user);
    public int countAll();
    public int countByCondition(String condition);
}
