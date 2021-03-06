package com.example.group.web.model;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import java.time.OffsetDateTime;

/**
 *Data transfer object representing a group.
 *
 * @author Siddharth Mehta
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupDto {

    @Null(message = "Group ID is auto generated - should be null")
    private Long groupId;

    @Null(message = "Creation date is auto generated - should be null")
    private OffsetDateTime createDate;

    @Null(message = "Last Modified date is auto generated - should be null")
    private OffsetDateTime lastModifiedDate;

    @NotBlank(message = "Group Name should cannot be Null")
    private String groupName;

    @NotBlank(message = "Group Description cannot be Null")
    private String groupDescription;
}
