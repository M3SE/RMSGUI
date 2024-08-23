package com.rms.model;

import com.rms.enums.UserRole;

public class Manager extends User {
    public Manager(String username, String passwordHash) {
        super(username, passwordHash, UserRole.MANAGER);
    }

    // Additional manager-specific methods can go here
}
