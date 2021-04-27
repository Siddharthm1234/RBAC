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
/*----------------- (Wrapper)Response from - Groups from User Name -------------------*/
public class GroupsList {
    private UserResponseDto user;
    private Set<GroupResponseDto> groups;
}
