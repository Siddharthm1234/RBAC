package com.example.role.web.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Getter
@Setter
@ToString
public class GroupRoleMappingDto {

    @Null
    private Long groupRoleId;

    @NotNull
    private Long groupId;

    @NotNull
    private Long roleId;

}
