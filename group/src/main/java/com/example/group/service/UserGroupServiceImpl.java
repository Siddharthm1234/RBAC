package com.example.group.service;

import com.example.group.domain.GroupRoleMapping;
import com.example.group.repository.GroupRoleRepository;
import com.example.group.repository.UserGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * Implementation for UserGroup Service
 *
 * @author Siddharth Mehta
 */
@Service
@RequiredArgsConstructor
public class UserGroupServiceImpl implements UserGroupService {
    private final UserGroupRepository userGroupRepository;
    private final GroupRoleRepository groupRoleRepository;

    /*-------------- Check if a User has a Role ---------------*/

    /**
     * This method is used to fulfill the request made from User-Service to check if User has Role permission
     * It will fetch all groupIds for user.
     *
     * @param userId Id of User
     * @return Set<Long> Set of groupIds.
     */
    @Override
    public Set<Long> getGroupIdsForUserId(Long userId) {
        Set<Long> groupIds = new HashSet<>();
        userGroupRepository.findByUserId(userId).forEach(userGroupMapping -> {
            groupIds.add(userGroupMapping.getGroupId());
        });
        return groupIds;
    }

    @Override
    /**
     * This method is used to fulfill the request made from User-Service to check if User has Role permission
     * It will fetch all roleIds for group and validate if specified roleId exist as mapping or not.
     * @param Set<Long> Set of GroupIds
     * @param roleId Id of Role to check
     * @return Boolean Boolean value to denote if User has Role permission
     */
    public Boolean getRoleIdsForGroupIds(Set<Long> groupIds, Long roleId) {
        for (Long groupId : groupIds) {
            Set<GroupRoleMapping> groupRoleRepositoryByGroupId = groupRoleRepository.findByGroupId(groupId);
            for (GroupRoleMapping groupRoleMapping : groupRoleRepositoryByGroupId) {
                if (groupRoleMapping.getRoleId() == (roleId)) {
                    return true;
                }
            }
        }
        return false;
    }
}
