package com.example.group.domain;

import com.example.group.constant.Constants;
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
        name = Constants.USER_GROUP_TABLE_NAME,
        uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "group_id"})}
)
public class UserGroupMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userGroupId;

    @Column(name = "user_id")
    private Long userId;
    @Column(name = "group_id")
    private Long groupId;

}
