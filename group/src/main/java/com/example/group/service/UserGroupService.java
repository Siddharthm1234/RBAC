package com.example.group.service;


import java.util.Set;

/**
 * Interface that provides contract for UserGroup Service
 *
 * @author Siddharth Mehta
 */
public interface UserGroupService {
    Set<Long> getGroupIdsForUserId(Long userId);

    Boolean getRoleIdsForGroupIds(Set<Long> groupIds, Long roleId);
}
