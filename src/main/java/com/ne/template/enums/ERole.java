package com.ne.template.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ERole {
    ADMIN("Admin"),CUSTOMER("Customer");
    private final String roleName;

    ERole(String roleName) {
        this.roleName = roleName;
    }

    @JsonValue
    public String getRoleName() {
        return roleName;
    }

    @JsonCreator
    public static ERole fromRoleName(String roleName) {
        for (ERole role : ERole.values()) {
            if (role.roleName.equalsIgnoreCase(roleName)) {
                return role;
            }
        }
        throw new IllegalArgumentException("Unknown role name: " + roleName);
    }

}
