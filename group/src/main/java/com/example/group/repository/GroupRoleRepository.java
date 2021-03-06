package com.example.group.repository;

import com.example.group.domain.GroupRoleMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface GroupRoleRepository extends JpaRepository<GroupRoleMapping, Long> {
    Set<GroupRoleMapping> findByGroupId(Long groupId);
}
