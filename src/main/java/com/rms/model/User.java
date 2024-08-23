package com.rms.model;

import com.rms.enums.UserRole;

public class User {
    private String username;
    private String password;
    private UserRole role; // Assuming you have roles like STAFF, MANAGER, etc.

    public User(String username, String password, UserRole role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
