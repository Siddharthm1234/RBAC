package com.example.user.web.mapper;

import com.example.user.domain.UserGroupMapping;
import com.example.user.web.dto.requestDto.UserGroupMappingRequestDto;
import com.example.user.web.dto.responseDto.UserGroupMappingResponseDto;
import org.mapstruct.Mapper;

@Mapper
public interface UserGroupMapper {
    /*Flow:  UserGroupRequestDto   ->  UserGroup Entity   ->   UserGroupResponseDto*/

    UserGroupMapping userGroupMappingRequestDtoToUserGroup(UserGroupMappingRequestDto userGroupMappingRequestDto);

    UserGroupMappingResponseDto userGroupMappingToUserGroupMappingResponseDto(UserGroupMapping userGroupMapping);
}
