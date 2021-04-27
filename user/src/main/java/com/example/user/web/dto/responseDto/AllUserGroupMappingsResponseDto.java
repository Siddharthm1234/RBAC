package com.example.user.web.dto.responseDto;

import lombok.*;

import java.util.Set;

/**
 * Wrapper Response Data Transfer Object representing list of UserGroupMappings.
 *
 * @author Siddharth Mehta
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AllUserGroupMappingsResponseDto {
    private Set<UserGroupMappingResponseDto> userGroups;

}
