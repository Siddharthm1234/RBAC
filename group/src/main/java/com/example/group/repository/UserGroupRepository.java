package com.example.group.repository;

import com.example.group.domain.UserGroupMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;


@Repository
public interface UserGroupRepository extends JpaRepository<UserGroupMapping, Long> {
    Set<UserGroupMapping> findByUserId(Long userId);
}
