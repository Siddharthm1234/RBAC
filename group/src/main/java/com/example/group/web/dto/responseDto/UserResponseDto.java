package com.example.group.web.dto.responseDto;

import lombok.*;

import java.time.OffsetDateTime;

/**
 * Response Data Transfer Object representing User.
 *
 * @author Siddharth Mehta
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
/*----------------- (Wrapped) Response for - Groups from User Name -------------------*/
public class UserResponseDto {
    private Long userId;
    private OffsetDateTime createDate;
    private OffsetDateTime lastModifiedDate;
    private String userName;
    private String firstName;
    private String lastName;
}
