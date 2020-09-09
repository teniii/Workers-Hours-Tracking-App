package com.example.App.util;

public enum ApplicationPermissions {
//    ANGAJAT_READ("angajat:read"),
//    ANGAJAT_WRITE("angajat:write"),
//    COMPANY_READ("company:read"),
//    COMPANY_WRITE("company:write");
    ANGAJAT(""),
    COMPANY("");
    private final String permission;

    ApplicationPermissions(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
