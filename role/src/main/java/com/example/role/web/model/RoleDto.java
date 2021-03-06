package com.example.role.web.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class
RoleDto {

    @Null(message = "Role ID is auto generated - should be Null")
    private Long roleId;

    @Null(message = "Creation date is auto generated - should be Null")
    private OffsetDateTime createDate;

    @Null(message = "Last Modified date is auto generated - should be Null")
    private OffsetDateTime lastModifiedDate;

    @NotBlank(message = "Role Name cannot be Null")
    private String roleName;

    @NotBlank(message = "Role Description cannot be Null")
    private String roleDescription;
}
