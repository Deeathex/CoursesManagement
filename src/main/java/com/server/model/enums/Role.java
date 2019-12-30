package com.server.model.enums;


public enum Role {
    PROFESSOR,
    STUDENT;

    private static final String ROLE_PREFIX = "ROLE_";

    public String toRoleName() {
        return ROLE_PREFIX + toString();
    }
}
