package com.example.user.service;

import com.example.user.web.dto.requestDto.UserGroupMappingRequestDto;
import com.example.user.web.dto.requestDto.UserGroupMappingUpdateRequestDto;
import com.example.user.web.dto.responseDto.AllUserGroupMappingsResponseDto;
import com.example.user.web.dto.responseDto.UserGroupMappingResponseDto;

/**
 * Interface that provides contract for UserGroup Service
 *
 * @author Siddharth Mehta
 */
public interface UserGroupService {
    AllUserGroupMappingsResponseDto getAllUserGroupMappings();

    UserGroupMappingResponseDto getUserGroupMappingById(String userGroupStringId);

    UserGroupMappingResponseDto updateUserGroupMappingById(UserGroupMappingUpdateRequestDto userGroupMappingUpdateRequestDto);

    UserGroupMappingResponseDto createUserGroupMapping(UserGroupMappingRequestDto userGroupMappingRequestDto);

    UserGroupMappingResponseDto deleteByUserIdAndGroupId(String userName, Long groupId);
}
