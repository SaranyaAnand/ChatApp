package com.example.appy_saranya.newfirebase;

public class UserModel {
    public String name;
    public String email;

    // Default constructor required for calls to
    // DataSnapshot.getValue(UserModel.class)
    public UserModel() {
    }

    public UserModel(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
