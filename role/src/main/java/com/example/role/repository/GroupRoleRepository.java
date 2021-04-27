package com.example.role.repository;


import com.example.role.domain.GroupRoleMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository class for GroupRoleMapping domain objects
 *
 * @author Siddharth Mehta
 */
@Repository
public interface GroupRoleRepository extends JpaRepository<GroupRoleMapping, Long> {
    void deleteByRoleId(Long roleId);
}
