package com.example.user.web.dto.responseDto;

import lombok.*;

import java.util.Set;

/**
 * Wrapper Response Data Transfer Object representing list of Users.
 *
 * @author Siddharth Mehta
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AllUsersResponseDto {
    private Set<UserResponseDto> users;

}
