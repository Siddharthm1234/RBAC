package com.example.user.web.mapper;

import com.example.user.domain.UserGroupMapping;
import com.example.user.web.model.UserGroupMappingDto;
import org.mapstruct.Mapper;

@Mapper
public interface UserGroupMapper {
    UserGroupMapping userGroupMappingDtoToUserGroup(UserGroupMappingDto userGroupMappingDto);
    UserGroupMappingDto userGroupMappingToUserGroupDto(UserGroupMapping userGroupMapping);
}
