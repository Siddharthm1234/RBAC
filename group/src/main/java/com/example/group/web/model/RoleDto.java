package com.example.group.web.model;

import lombok.*;

import java.time.OffsetDateTime;

/**
 *Data transfer object representing a roles to corresponding group.
 *
 * @author Siddharth Mehta
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class RoleDto {

    private Long roleId;
    private OffsetDateTime createDate;
    private OffsetDateTime lastModifiedDate;
    private String roleName;
    private String roleDescription;

}
