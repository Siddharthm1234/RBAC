package com.example.group.domain;

import com.example.group.constant.Constants;
import lombok.*;

import javax.persistence.*;

/**
 * Simple JavaBean domain object representing a GroupRoleMapping.
 *
 * @author Siddharth Mehta
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@ToString
@Table(
        name = Constants.GROUP_ROLE_TABLE_NAME,
        uniqueConstraints = {@UniqueConstraint(columnNames = {"group_id", "role_id"})}
)
public class GroupRoleMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long groupRoleId;

    @Column(name = "group_id")
    private Long groupId;
    @Column(name = "role_id")
    private Long roleId;
}
