package com.schoolproject.javafxmoviesapp.Entity;

import java.util.ArrayList;
import java.util.List;

public class Role {
    private int id;
    private String name;

    private List<String> permissions = new ArrayList<>();

    public Role() {
    }

    public Role(String name, List<String> permissions) {
        this.name = name;
        this.permissions = permissions;
    }

    public Role(int id, String name, List<String> permissions) {
        this.id = id;
        this.name = name;
        this.permissions = permissions;
    }

    @Override
    public String toString() {
        return name;
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

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }
}