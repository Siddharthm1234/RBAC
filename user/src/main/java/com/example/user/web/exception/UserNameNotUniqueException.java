package com.example.user.web.exception;

public class UserNameNotUniqueException extends RuntimeException {
    public UserNameNotUniqueException(String message) {
        super(message);
    }
}
