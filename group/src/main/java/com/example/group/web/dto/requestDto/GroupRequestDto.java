package com.example.group.web.dto.requestDto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;

/**
 * Request Data Transfer Object for Group.
 *
 * @author Siddharth Mehta
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GroupRequestDto {
    @Null(message = "Group ID Is Auto-Generated - Should Not Be Set.")
    private Long groupId;

    @NotBlank(message = "Group Name Cannot be Empty.")
    private String groupName;

    @NotBlank(message = "Group Description Cannot be Empty.")
    private String groupDescription;
}
