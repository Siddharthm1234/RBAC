package com.example.group.web.dto.requestDto;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**
 * Request Data Transfer Object for GroupRoleMappings.
 *
 * @author Siddharth Mehta
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GroupRoleMappingRequestDto {

    @Null(message = "Group-Role ID Is Auto-Generated - Should Not Be Set.")
    private Long groupRoleId;

    @NotNull(message = "Group ID Cannot Be Empty.")
    private Long groupId;

    @NotNull(message = "Role ID Cannot Be Empty.")
    private Long roleId;

}
