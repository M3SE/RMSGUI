package com.rms.service;

import com.rms.model.User;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class UserService {
    private Map<String, User> users;

    public UserService() {
        users = new HashMap<>();
        // Load users from persistent storage if applicable
    }

    public User authenticate(String username, String password) {
        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;  // Successful authentication
        }
        return null;  // Authentication failed
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    public boolean registerUser(User user) {
        if (users.containsKey(user.getUsername())) {
            return false;
        }
        users.put(user.getUsername(), user);
        // Save to persistent storage if applicable
        return true;
    }

    public boolean removeUser(String username) {
        if (users.containsKey(username)) {
            users.remove(username);
            // Save to persistent storage if applicable
            return true;
        }
        return false;
    }

    public User getUserByUsername(String username) {
        return users.get(username);
    }

    // Other methods like authenticate(), etc.
}
