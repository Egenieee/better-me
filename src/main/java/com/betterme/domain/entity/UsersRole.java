package com.betterme.domain.entity;

import lombok.Getter;

@Getter
public enum UsersRole {

    ADMIN("ROLE_ADMIN"),
    USERS("ROLE_USERS");

    private String value;

    UsersRole(String value) {
        this.value = value;
    }
}
