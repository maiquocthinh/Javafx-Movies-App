package com.schoolproject.javafxmoviesapp.Entity;

public class Role {
    private String name;
    private int id;

    @Override
    public String toString() {
        return name;
    }

    public Role(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}