package com.schoolproject.javafxmoviesapp.Utils;

import com.schoolproject.javafxmoviesapp.Entity.Role;
import com.schoolproject.javafxmoviesapp.Entity.User;

public final class AppSessionUtil {
    private static AppSessionUtil instance = null;
    private User user;
    private Role role;

    public static AppSessionUtil getInstance() {
        if (instance == null) instance = new AppSessionUtil();
        return instance;
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
