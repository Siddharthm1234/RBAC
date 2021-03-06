package com.example.group.web.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**
 *Data transfer object representing a group-role-mapping.
 *
 * @author Siddharth Mehta
 */
@ToString
@Getter
@Setter
public class GroupRoleMappingDto {

    @Null(message = "Group-Role ID is auto generated - should be null")
    private Long groupRoleId;

    @NotNull(message = "Group ID cannot be Null")
    private Long groupId;

    @NotNull(message = "Role ID cannot be Null")
    private Long roleId;
}
