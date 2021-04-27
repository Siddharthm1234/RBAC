package com.example.user.web.dto.responseDto;

import lombok.*;

import java.time.OffsetDateTime;

/**
 * Response Data Transfer Object representing User.
 *
 * @author Siddharth Mehta
 */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserResponseDto {
    private Long userId;
    private OffsetDateTime createDate;
    private OffsetDateTime lastModifiedDate;
    private String userName;
    private String firstName;
    private String lastName;
}
