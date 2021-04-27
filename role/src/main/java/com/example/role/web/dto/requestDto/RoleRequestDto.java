package com.example.role.web.dto.requestDto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;

/**
 * Request Data Transfer Object for Role.
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
RoleRequestDto {

    @Null(message = "Role ID Is Auto Generated - Should Not Be Set.")
    private Long roleId;

    @NotBlank(message = "Role Name Cannot Be Empty.")
    private String roleName;

    @NotBlank(message = "Role Description Cannot Be Empty.")
    private String roleDescription;
}
