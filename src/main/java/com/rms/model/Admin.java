package com.rms.model;

import com.rms.enums.UserRole;

public class Admin extends User {
    public Admin(String username, String passwordHash) {
        super(username, passwordHash, UserRole.MANAGER);
    }

    // Additional admin-specific methods can go here
}
