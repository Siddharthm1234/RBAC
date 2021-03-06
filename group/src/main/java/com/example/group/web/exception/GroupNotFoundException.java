package com.example.group.web.exception;

public class GroupNotFoundException extends RuntimeException{

    public GroupNotFoundException(String message){
        super(message);
    }
}
