package com.schoolproject.javafxmoviesapp.Entity;

public class User {
    private int id;
    private String name;
    private String email;
    private String avatar;
    private String password;
    private int roleId;

    public User() {
    }

    public User(int id, String name, String email, String avatar, String password, int roleId) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.avatar = avatar;
        this.password = password;
        this.roleId = roleId;
    }

    public User(String name, String email, String avatar, String password, int roleId) {
        this.name = name;
        this.email = email;
        this.avatar = avatar;
        this.password = password;
        this.roleId = roleId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
}
