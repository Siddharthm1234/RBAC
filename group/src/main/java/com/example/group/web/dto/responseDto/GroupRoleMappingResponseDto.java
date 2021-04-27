package com.example.group.web.dto.responseDto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**
 * Response Data Transfer Object representing GroupRoleMappings.
 *
 * @author Siddharth Mehta
 */
@ToString
@Getter
@Setter
public class GroupRoleMappingResponseDto {

    @Null(message = "Group-Role ID is auto generated - should be null")
    private Long groupRoleId;

    @NotNull(message = "Group ID cannot be Null")
    private Long groupId;

    @NotNull(message = "Role ID cannot be Null")
    private Long roleId;
}
