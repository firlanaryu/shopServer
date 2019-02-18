package com.creaginetech.shopserver.models;

public class Category {

    String name;
    String uid;

    public Category() {
    }

    public Category(String name) {
        this.name = name;
    }

    public Category(String name, String uid) {
        this.name = name;
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
