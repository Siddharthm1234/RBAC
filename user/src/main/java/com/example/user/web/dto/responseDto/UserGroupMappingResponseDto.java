package com.example.user.web.dto.responseDto;

import lombok.*;

/**
 * Response Data Transfer Object representing UserGroupMappings.
 *
 * @author Siddharth Mehta
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserGroupMappingResponseDto {
    private Long userGroupId;
    private Long userId;
    private Long groupId;
}
