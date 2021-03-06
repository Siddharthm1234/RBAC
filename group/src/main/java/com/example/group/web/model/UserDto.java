package com.example.group.web.model;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import java.time.OffsetDateTime;

/**
 *Data transfer object for users.
 *
 * @author Siddharth Mehta
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDto {
    private Long userId;
    private OffsetDateTime createDate;
    private OffsetDateTime lastModifiedDate;
    private String userName;
    private String firstName;
    private String lastName;
}
