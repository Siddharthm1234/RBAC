package com.example.user.web.dto.requestDto;

import lombok.*;

import javax.validation.constraints.NotNull;

/**
 * Request Data Transfer Object for updating UserGroupMappings (userGroupId not null).
 *
 * @author Siddharth Mehta
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserGroupMappingUpdateRequestDto {

    @NotNull(message = "User-Group ID Cannot Be Empty.")
    private Long userGroupId;

    @NotNull(message = "User ID Cannot Be Empty.")
    private Long userId;

    @NotNull(message = "Group ID Cannot Be Empty.")
    private Long groupId;

}
