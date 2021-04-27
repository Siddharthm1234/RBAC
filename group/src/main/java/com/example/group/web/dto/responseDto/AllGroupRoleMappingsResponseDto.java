package com.example.group.web.dto.responseDto;

import lombok.*;

import java.util.Set;

/**
 * Wrapper Response Data Transfer Object representing list of GroupRoleMappings.
 *
 * @author Siddharth Mehta
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AllGroupRoleMappingsResponseDto {
    private Set<GroupRoleMappingResponseDto> groupRoles;
}
