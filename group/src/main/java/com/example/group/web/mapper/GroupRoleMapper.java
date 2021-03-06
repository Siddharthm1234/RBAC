package com.example.group.web.mapper;

import com.example.group.domain.GroupRoleMapping;
import com.example.group.web.model.GroupRoleMappingDto;
import org.mapstruct.Mapper;

@Mapper
public interface GroupRoleMapper {
    GroupRoleMapping groupRoleMappingDtoToGroupRoleMapping(GroupRoleMappingDto groupRoleMappingDto);
    GroupRoleMappingDto groupRoleMappingToGroupRoleMappingDto(GroupRoleMapping groupRoleMapping);
}
