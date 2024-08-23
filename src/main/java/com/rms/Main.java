package com.rms.util;

import com.rms.enums.UserRole;
import com.rms.model.User;
import com.rms.service.UserService;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserService();

        // Register a new user
        User user = new User("admin", PasswordUtil.hashPassword("admin123"), UserRole.MANAGER);
        userService.registerUser(user);

        // Authenticate the user
        User authenticatedUser = userService.authenticate("admin", "admin123");
        if (authenticatedUser != null) {
            System.out.println("User authenticated: " + authenticatedUser.getUsername());
        } else {
            System.out.println("Authentication failed.");
        }

        // Continue with other operations...
    }
}
