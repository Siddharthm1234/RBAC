package com.example.role.web.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.OffsetDateTime;

/**
 * Response Data Transfer Object representing Group.
 *
 * @author Siddharth Mehta
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
/*----------------- (Wrapped) Response for - Roles from Group ID -------------------*/
public class GroupResponseDto {

    private Long groupId;
    private OffsetDateTime createDate;
    private OffsetDateTime lastModifiedDate;
    private String groupName;
    private String groupDescription;
}