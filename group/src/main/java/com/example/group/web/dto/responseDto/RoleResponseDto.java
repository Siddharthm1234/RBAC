package com.example.group.web.dto.responseDto;

import lombok.*;

import java.time.OffsetDateTime;

/**
 * Response Data Transfer Object representing Role.
 *
 * @author Siddharth Mehta
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
/*----------------- (Wrapped) Response from - Roles from Group ID -------------------*/
public class RoleResponseDto {

    private Long roleId;
    private OffsetDateTime createDate;
    private OffsetDateTime lastModifiedDate;
    private String roleName;
    private String roleDescription;

}
