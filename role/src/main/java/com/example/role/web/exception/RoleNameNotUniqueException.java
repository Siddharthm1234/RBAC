package com.example.role.web.exception;

public class RoleNameNotUniqueException extends RuntimeException {
    public RoleNameNotUniqueException(String message) {
        super(message);
    }
}
