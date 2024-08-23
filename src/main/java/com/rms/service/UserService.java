package com.rms.service;

import com.rms.model.User;
import com.rms.util.FileManager;
import com.rms.util.PasswordUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserService {
    private Map<String, User> users;
    private final String filePath = "data/users.json";

    public UserService() {
        try {
            users = FileManager.loadUsers(filePath);
            if (users == null) {
                users = new HashMap<>();
            }
        } catch (IOException e) {
            users = new HashMap<>();
            e.printStackTrace();
        }
    }

    public boolean registerUser(User user) {
        if (users.containsKey(user.getUsername())) {
            return false; // User already exists
        }
        users.put(user.getUsername(), user);
        saveUsers();
        return true;
    }

    public User authenticate(String username, String password) {
        User user = users.get(username);
        if (user != null && PasswordUtil.verifyPassword(password, user.getPasswordHash())) {
            return user;
        }
        return null; // Authentication failed
    }

    public User getUserByUsername(String username) {
        return users.get(username);
    }

    private void saveUsers() {
        try {
            FileManager.saveUsers(users, filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
