package com.example.socialmedia;
public class User {
    public String name;
    public String email;
    public String bio;

    public User() {} // Required for Firebase

    public User(String name, String email, String bio) {
        this.name = name;
        this.email = email;
        this.bio = bio;
    }

    public String getName() { return name != null ? name : ""; }
    public String getEmail() { return email != null ? email : ""; }
    public String getBio() { return bio != null ? bio : ""; }

}
