package com.example.role.web.exception;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Provides consistent Error Message format across the entire application
 *
 * @author Siddharth Mehta
 */
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionResponse {
    private Date timeStamp;
    private Integer errorCode;
    private String message;
    private String details;


    public Date getTimeStamp() {
        return timeStamp;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }
}