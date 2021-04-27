package com.example.user.web.dto.responseDto;

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
    private UserResponseDto user;
    private Set<GroupResponseDto> groups;
    private Set<RoleResponseDto> roles;
}
