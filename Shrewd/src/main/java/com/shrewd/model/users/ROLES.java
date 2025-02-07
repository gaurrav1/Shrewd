package com.shrewd.model.users;

import lombok.Getter;

@Getter
public enum ROLES {
    ORGANIZATION("Organization", "Handles the overall organization management"),
    ADMIN("Admin", "Manages users, settings, and policies"),
    HR("HR", "Handles users relations and payroll"),
    MANAGER("Manager", "Oversees teams and operations"),
    EMPLOYEE("Employee", "Works on the tasks assigned by managers");

    private final String roleName;
    private final String description;

    ROLES(String roleName, String description) {
        this.roleName = roleName;
        this.description = description;
    }

}
