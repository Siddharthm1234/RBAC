package com.example.group.service;

import com.example.group.web.dto.requestDto.GroupRoleMappingRequestDto;
import com.example.group.web.dto.requestDto.GroupRoleMappingUpdateRequestDto;
import com.example.group.web.dto.responseDto.AllGroupRoleMappingsResponseDto;
import com.example.group.web.dto.responseDto.GroupRoleMappingResponseDto;

/**
 * Interface that provides contract for GroupRole Service
 *
 * @author Siddharth Mehta
 */
public interface GroupRoleService {
    AllGroupRoleMappingsResponseDto getAllGroupRoleMappings();

    GroupRoleMappingResponseDto getGroupRoleMappingById(Long groupRoleId);

    GroupRoleMappingResponseDto updateGroupRoleMappingById(GroupRoleMappingUpdateRequestDto groupRoleMappingUpdateRequestDto);

    GroupRoleMappingResponseDto createGroupRoleMapping(GroupRoleMappingRequestDto groupRoleMappingRequestDto);

    GroupRoleMappingResponseDto deleteByGroupIdAndRoleId(Long groupId, Long roleId);

}
