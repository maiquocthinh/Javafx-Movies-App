package com.schoolproject.javafxmoviesapp.DAO.Interface;

import java.util.List;

public interface NotificationDAO<T> extends BaseDAO<T>{
    public List<T> selectByUserId(int userId);

}
