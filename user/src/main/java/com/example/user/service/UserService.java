package com.example.user.service;

import com.example.user.web.dto.requestDto.UserRequestDto;
import com.example.user.web.dto.responseDto.AllCredentialList;
import com.example.user.web.dto.responseDto.AllUsersResponseDto;
import com.example.user.web.dto.responseDto.GroupsList;
import com.example.user.web.dto.responseDto.UserResponseDto;

/**
 * Interface that provides contract for User Service
 *
 * @author Siddharth Mehta
 */
public interface UserService {
    AllUsersResponseDto getAllUsers();

    UserResponseDto getUserByName(String userName);

    UserResponseDto updateUserByName(UserRequestDto userRequestDto);

    UserResponseDto createUser(UserRequestDto userRequestDto);

    UserResponseDto deleteByName(String userName);

    /*----------------- Groups from UserName -------------------*/
    GroupsList getGroupsByUserName(String userName);

    /*-------------- Check if a User belongs to Group ---------------*/
    String checkGroupIdForUserName(String userName, Long groupId);

    /*-------------- Check if a User has a Role ---------------*/
    String checkRoleIdForUserName(String userName, Long roleId);

    /*-------------- Groups and Roles from UserName ----------------*/
    AllCredentialList getGroupsAndRolesByUserName(String userName);
}
