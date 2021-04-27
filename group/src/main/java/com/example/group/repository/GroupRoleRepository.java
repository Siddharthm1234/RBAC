package com.example.group.repository;

import com.example.group.domain.GroupRoleMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

/**
 * Repository class for GroupRoleMapping domain objects
 *
 * @author Siddharth Mehta
 */
@Repository
public interface GroupRoleRepository extends JpaRepository<GroupRoleMapping, Long> {
    Set<GroupRoleMapping> findByGroupId(Long groupId);

    void deleteByGroupId(Long groupId);

    Optional<GroupRoleMapping> findByGroupIdAndAndRoleId(Long groupId, Long roleId);

    void deleteByGroupIdAndRoleId(Long groupId, Long roleId);

}
