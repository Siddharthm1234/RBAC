package com.example.group.web.dto.responseDto;

import lombok.*;

/**
 * Response Data Transfer Object representing UserGroupMappings.
 *
 * @author Siddharth Mehta
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
/*----------------- Request for - Groups from User Name -------------------*/
public class UserGroupMappingResponseDto {
    private Long userGroupId;
    private Long userId;
    private Long groupId;
}
