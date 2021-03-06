package com.example.group.service;

import com.example.group.web.model.GroupRoleMappingDto;

import java.util.Set;

public interface GroupRoleService {
    Set<GroupRoleMappingDto> getGroupRoleMapping();
    GroupRoleMappingDto getGroupRoleMappingById(Long groupRoleId);
    GroupRoleMappingDto updateGroupRoleMappingById(Long groupRoleId, GroupRoleMappingDto groupRoleMappingDtoDto);
    GroupRoleMappingDto createGroupRoleMapping(GroupRoleMappingDto groupRoleMappingDto);
    void deleteById(Long groupRoleId);
}
