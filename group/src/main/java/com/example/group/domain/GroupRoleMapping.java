package com.example.group.domain;

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
        name = "GROUP_ROLE_MAPPING_TABLE",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"group_id", "role_id"})}
)
public class GroupRoleMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long groupRoleId;

    @Column(name="group_id")
    private Long groupId;
    @Column(name="role_id")
    private Long roleId;
}
