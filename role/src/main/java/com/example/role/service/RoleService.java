package com.example.role.service;

import com.example.role.web.model.RoleDto;

import java.util.Set;

public interface RoleService {

    Set<RoleDto> getRoles();
    RoleDto getRoleById(Long roleId);
    RoleDto updateRoleById(Long roleId, RoleDto roleDto);
    RoleDto createRole(RoleDto roleDto);
    void deleteById(Long roleId);


}
