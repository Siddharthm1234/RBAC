package com.example.group.web.dto.responseDto;

import lombok.*;

import java.util.Set;

/**
 * Wrapper Response Data Transfer Object representing list of Groups.
 *
 * @author Siddharth Mehta
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AllGroupsResponseDto {
    private Set<GroupResponseDto> groups;

}
