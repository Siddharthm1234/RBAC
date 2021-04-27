package com.example.role.web.dto.responseDto;


import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * Response Data Transfer Object representing Group with corresponding Roles.
 *
 * @author Siddharth Mehta
 */
@Getter
@Setter
/*----------------- (Wrapper)Response for - Roles from Group ID -------------------*/
public class RolesList {
    private GroupResponseDto group;
    private Set<RoleResponseDto> roles;
}