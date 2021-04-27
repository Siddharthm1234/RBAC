package com.example.group.constant;

/**
 * Single Access Point for all Constants present in application
 *
 * @author Siddharth Mehta
 */
public class Constants {
    private Constants() {
    }

    public static final String GROUP_TABLE_NAME = "GROUP_TABLE";
    public static final String USER_GROUP_TABLE_NAME = "USER_GROUP_MAPPING_TABLE";
    public static final String GROUP_ROLE_TABLE_NAME = "GROUP_ROLE_MAPPING_TABLE";


    public static final int USER_NOT_FOUND = 40401;
    public static final int GROUP_NOT_FOUND = 40402;
    public static final int ROLE_NOT_FOUND = 40403;
    public static final int USER_GROUP_NOT_FOUND = 40404;
    public static final int GROUP_ROLE_NOT_FOUND = 40405;

    public static final int USER_SERVICE_INVALID_METHOD_ARGUMENT = 40001;
    public static final int GROUP_SERVICE_INVALID_METHOD_ARGUMENT = 40002;
    public static final int ROLE_SERVICE_INVALID_METHOD_ARGUMENT = 40003;

    public static final int USER_NAME_DATA_INTEGRITY_VIOLATION = 40901;
    public static final int GROUP_NAME_DATA_INTEGRITY_VIOLATION = 40902;
    public static final int ROLE_NAME_DATA_INTEGRITY_VIOLATION = 40903;
    public static final int USER_GROUP_DATA_INTEGRITY_VIOLATION = 40904;
    public static final int GROUP_ROLE_DATA_INTEGRITY_VIOLATION = 40905;

    public static final int GROUP_SERVICE_DOWN = 50001;
    public static final int ROLE_SERVICE_DOWN = 50002;
}
