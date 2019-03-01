package com.example.cmput301w19t15;

import android.media.Image;

public class User {
    private String username;
    private String name;
    private String email;
    private String phone;
    private Float rating;

    public User(String username, String name, String email, String phone) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }
    public void setUsername(String username) {
    }
    public String getUsername() {
        return username;
    }
    public void setName(String name) {
    }

    public String getName() {
        return name;
    }
    public void setEmail(String email) {
    }
    public String getEmail() {
        return email;
    }
    public void setPhone(String phone) {
    }
    public String getPhone() {
        return phone;
    }
    public void setRating(Float rating) {
    }
    public Float getRating() {
        return 0f ;
    }
}
