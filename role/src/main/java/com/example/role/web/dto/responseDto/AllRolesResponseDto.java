package com.example.role.web.dto.responseDto;

import lombok.*;

import java.util.Set;

/**
 * Wrapper Response Data Transfer Object representing list of Roles.
 *
 * @author Siddharth Mehta
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AllRolesResponseDto {
    private Set<RoleResponseDto> roles;

}
