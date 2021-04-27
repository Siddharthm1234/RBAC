package com.example.user.web.dto.responseDto;

import lombok.*;

import java.time.OffsetDateTime;

/**
 * Response Data Transfer Object representing Group used in GroupsList.class.
 *
 * @author Siddharth Mehta
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
/*----------------- (Wrapped)Response from - Groups from User Name -------------------*/
public class GroupResponseDto {
    private Long groupId;
    private OffsetDateTime createDate;
    private OffsetDateTime lastModifiedDate;
    private String groupName;
    private String groupDescription;
}
