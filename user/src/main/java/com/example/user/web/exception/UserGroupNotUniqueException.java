package com.example.user.web.exception;

public class UserGroupNotUniqueException extends RuntimeException {
    public UserGroupNotUniqueException(String message) {
        super(message);
    }
}
