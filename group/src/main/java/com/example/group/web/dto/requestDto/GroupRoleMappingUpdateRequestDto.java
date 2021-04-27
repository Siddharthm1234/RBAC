package com.example.group.web.dto.requestDto;

import lombok.*;

import javax.validation.constraints.NotNull;

/**
 * Request Data Transfer Object for updating GroupRoleMappings (groupRoleId not null).
 *
 * @author Siddharth Mehta
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GroupRoleMappingUpdateRequestDto {

    @NotNull(message = "Group-Role ID Cannot Be Empty.")
    private Long groupRoleId;

    @NotNull(message = "Group ID Cannot Be Empty.")
    private Long groupId;

    @NotNull(message = "Role ID Cannot Be Empty.")
    private Long roleId;

}
