package com.schoolproject.javafxmoviesapp.DAO.Interface;

public interface CommentDAO<T> extends BaseDAO<T> {
    public int countAll();
    public int countByCondition(String condition);
}
