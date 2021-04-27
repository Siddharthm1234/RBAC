package com.example.group.web.exception;

public class GroupNameNotUniqueException extends RuntimeException {
    public GroupNameNotUniqueException(String message) {
        super(message);
    }
}
