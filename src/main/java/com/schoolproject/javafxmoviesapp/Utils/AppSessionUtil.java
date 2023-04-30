package com.schoolproject.javafxmoviesapp.Utils;

import com.schoolproject.javafxmoviesapp.DAO.Concrete.RoleDAOImpl;
import com.schoolproject.javafxmoviesapp.DAO.Concrete.UserDAOImpl;
import com.schoolproject.javafxmoviesapp.Entity.Role;
import com.schoolproject.javafxmoviesapp.Entity.User;
import javafx.stage.Stage;

public final class AppSessionUtil {
    private static AppSessionUtil instance = null;
    private User user;
    private Role role;
    private Stage adminStage = null;

    public static AppSessionUtil getInstance() {
        if (instance == null) instance = new AppSessionUtil();
        return instance;
    }

    public void refresh() {
        user = UserDAOImpl.getInstance().findById(user.getId());
        role = RoleDAOImpl.getInstance().findByUser(user);
    }

    public void clear() {
        user = null;
        role = null;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Stage getAdminStage() {
        if (adminStage == null) adminStage = new Stage();
        return adminStage;
    }

    public void setAdminStage(Stage adminStage) {
        this.adminStage = adminStage;
    }

    @Override
    public String toString() {
        return String.format("""
                        Session: {
                            User: {
                                id: '%d',
                                name: '%s',
                                email: '%s',
                            },
                            Role: {
                                name: '%s',
                                permissions: '%s'
                            }
                        }""",
                user.getId(),
                user.getName(),
                user.getEmail(),
                role.getName(),
                role.getPermissions().toString()
        );
    }
}
