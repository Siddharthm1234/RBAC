package com.example.group.web.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * Wrapper Object to  provide a data transfer object of role list along with groups.
 *
 * @author Siddharth Mehta
 */
@Getter
@Setter
public class RolesList {
    private GroupDto groupDto;
    private Set<RoleDto> rolesSet;
}
