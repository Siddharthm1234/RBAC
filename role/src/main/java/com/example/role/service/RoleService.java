package com.example.role.service;

import com.example.role.web.dto.requestDto.RoleRequestDto;
import com.example.role.web.dto.requestDto.RoleUpdateRequestDto;
import com.example.role.web.dto.responseDto.AllRolesResponseDto;
import com.example.role.web.dto.responseDto.GroupRoleMappingResponseDto;
import com.example.role.web.dto.responseDto.RoleResponseDto;

import java.util.Set;

/**
 * Interface that provides contract for Group Service
 *
 * @author Siddharth Mehta
 */
public interface RoleService {
    AllRolesResponseDto getAllRoles();

    RoleResponseDto getRoleById(Long roleId);

    RoleResponseDto updateRoleById(RoleUpdateRequestDto roleUpdateRequestDto);

    RoleResponseDto createRole(RoleRequestDto roleRequestDto);

    RoleResponseDto deleteById(Long roleId);

    /*----------------- Roles from Group Id -------------------*/
    Set<RoleResponseDto> getRolesByGroupId(Set<GroupRoleMappingResponseDto> groupRoleMappingResponseDtos);
}
