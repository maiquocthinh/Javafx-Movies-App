package com.schoolproject.javafxmoviesapp.DAO.Concrete;

import com.schoolproject.javafxmoviesapp.DAO.Interface.UserDAO;
import com.schoolproject.javafxmoviesapp.Entity.User;

public class UserDAOImpl implements UserDAO<User> {
    private static UserDAOImpl userDAOImpl = null;
    public static UserDAOImpl getInstance(){
        if(userDAOImpl!=null){
            return userDAOImpl;
        }else{
            userDAOImpl = new UserDAOImpl();
            return userDAOImpl;
        }
    }
}
