package com.example.googleplacesofintrest.model;

public class User {
    private String emailAddress;
    private String password;

    public User(String emailAddress, String userName) {
        this.emailAddress = emailAddress;
        password = userName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
