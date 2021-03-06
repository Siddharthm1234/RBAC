package com.example.group.web.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**
 *Data transfer object representing a user-group-mapping.
 *
 * @author Siddharth Mehta
 */
@ToString
@Getter
@Setter
public class UserGroupMappingDto {

    @Null(message = "User-Group ID is auto generated - should be null")
    private Long userGroupId;

    @NotNull(message = "User ID cannot be Null")
    private Long userId;

    @NotNull(message = "Group ID cannot be Null")
    private Long groupId;

}
