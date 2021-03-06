package com.example.group.service;

import com.example.group.domain.GroupRoleMapping;
import com.example.group.repository.GroupRoleRepository;
import com.example.group.repository.UserGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserGroupServiceImpl implements UserGroupService {
    private final UserGroupRepository userGroupRepository;
    private final GroupRoleRepository groupRoleRepository;

    @Override
    public Set<Long> getGroupIdsForUserId(Long userId) {
        Set<Long> groupIds = new HashSet<>();
        userGroupRepository.findByUserId(userId).forEach(userGroupMapping -> {
            groupIds.add(userGroupMapping.getGroupId());
        });
        return groupIds;
    }

    @Override
    public Boolean getRoleIdsForGroupIds(Set<Long> groupIds, Long roleId) {
        for(Long groupId : groupIds){
            Set<GroupRoleMapping> groupRoleRepositoryByGroupId = groupRoleRepository.findByGroupId(groupId);
            for(GroupRoleMapping groupRoleMapping : groupRoleRepositoryByGroupId){
                if(groupRoleMapping.getRoleId()==(roleId)){
                    return true;
                }
            }
        }
        return false;
    }
}
