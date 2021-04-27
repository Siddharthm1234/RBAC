package com.example.group.web.dto.responseDto;

import lombok.*;

import java.util.Set;

/**
 * Response Data Transfer Object representing User with corresponding Groups.
 *
 * @author Siddharth Mehta
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AllCredentialList {
    private Set<GroupResponseDto> groups;
    private Set<RoleResponseDto> roles;
}
