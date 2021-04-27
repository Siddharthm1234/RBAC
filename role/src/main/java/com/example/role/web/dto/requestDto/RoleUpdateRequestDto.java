package com.example.role.web.dto.requestDto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


/**
 * Request Data Transfer Object for updating Roles (roleId not null).
 *
 * @author Siddharth Mehta
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class
RoleUpdateRequestDto {
    @NotNull(message = "Role ID Cannot Be Empty.")
    private Long roleId;

    @NotBlank(message = "Role Name Cannot Be Empty.")
    private String roleName;

    @NotBlank(message = "Role Description Cannot Be Empty.")
    private String roleDescription;
}
