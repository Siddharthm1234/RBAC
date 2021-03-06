package com.example.role.web.mapper;

import com.example.role.domain.Role;
import com.example.role.web.model.RoleDto;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
public interface RoleMapper {
    Role roleDtoToRole(RoleDto roleDto);
    RoleDto roleToRoleDto(Role role);
}
