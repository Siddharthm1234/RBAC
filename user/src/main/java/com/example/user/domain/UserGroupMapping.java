package com.example.user.domain;

import lombok.*;

import javax.persistence.*;

/**
 * Simple JavaBean domain object representing a UserGroupMapping.
 *
 * @author Siddharth Mehta
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
        name = "USER_GROUP_MAPPING_TABLE",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id" ,"group_id"})}
)
public class UserGroupMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userGroupId;

    @Column(name="user_id")
    private Long userId;
    @Column(name="group_id")
    private Long groupId;

}
