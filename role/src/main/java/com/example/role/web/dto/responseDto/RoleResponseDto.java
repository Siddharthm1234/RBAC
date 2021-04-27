package com.example.role.web.dto.responseDto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import java.time.OffsetDateTime;

/**
 * Response Data Transfer Object representing Role.
 *
 * @author Siddharth Mehta
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class RoleResponseDto {

    @Null(message = "Role ID is auto generated - should be Null")
    private Long roleId;

    @Null(message = "Creation date is auto generated - should be Null")
    private OffsetDateTime createDate;

    @Null(message = "Last Modified date is auto generated - should be Null")
    private OffsetDateTime lastModifiedDate;

    @NotBlank(message = "Role Name cannot be Null")
    private String roleName;

    @NotBlank(message = "Role Description cannot be Null")
    private String roleDescription;
}
