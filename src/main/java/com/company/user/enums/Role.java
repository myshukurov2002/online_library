package com.company.user.enums;

public enum Role {
    SUPER_ADMIN, ADMIN, MODERATOR, SUPPLIER, USER;

    public static Role[] getAllRoles() {
        return values();
    }
}
