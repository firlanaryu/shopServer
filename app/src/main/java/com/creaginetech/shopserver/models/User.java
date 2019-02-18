package com.creaginetech.shopserver.models;

public class User {

    // [START blog_user_class]
    public String username;
    public String email;
    public String address;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public User(String username, String email, String address) {
        this.username = username;
        this.email = email;
        this.address = address;
    }
}
// [END blog_user_class]
