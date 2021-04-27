package com.example.user.web.dto.requestDto;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**
 * Request Data Transfer Object for UserGroupMappings.
 *
 * @author Siddharth Mehta
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserGroupMappingRequestDto {

    @Null(message = "User-Group ID Is Auto-Generated - Should Not Be Set.")
    private Long userGroupId;

    @NotNull(message = "User ID Cannot Be Empty.")
    private Long userId;

    @NotNull(message = "Group ID Cannot Be Empty.")
    private Long groupId;

}
