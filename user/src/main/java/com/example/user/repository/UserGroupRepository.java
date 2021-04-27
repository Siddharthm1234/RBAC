package com.example.user.repository;

import com.example.user.domain.UserGroupMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

/**
 * Repository class for UserGroupMapping domain objects
 *
 * @author Siddharth Mehta
 */
@Repository
public interface UserGroupRepository extends JpaRepository<UserGroupMapping, Long> {
    Set<UserGroupMapping> findByUserId(Long userId);

    Optional<UserGroupMapping> findUserGroupMappingByUserIdAndGroupId(Long userId, Long groupId);

    void deleteByUserId(Long userId);

    void deleteByUserIdAndGroupId(Long userId, Long groupId);
}
