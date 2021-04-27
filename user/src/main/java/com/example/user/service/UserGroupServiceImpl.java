package com.example.user.service;

import com.example.user.domain.User;
import com.example.user.domain.UserGroupMapping;
import com.example.user.repository.UserGroupRepository;
import com.example.user.repository.UserRepository;
import com.example.user.web.exception.UserGroupNotFoundException;
import com.example.user.web.exception.UserGroupNotUniqueException;
import com.example.user.web.exception.UserNotFoundException;
import com.example.user.web.mapper.UserGroupMapper;
import com.example.user.web.dto.requestDto.UserGroupMappingRequestDto;
import com.example.user.web.dto.requestDto.UserGroupMappingUpdateRequestDto;
import com.example.user.web.dto.responseDto.AllUserGroupMappingsResponseDto;
import com.example.user.web.dto.responseDto.UserGroupMappingResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Implementation for UserGroup Service
 *
 * @author Siddharth Mehta
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserGroupServiceImpl implements UserGroupService {

    private final UserGroupRepository userGroupRepository;
    private final UserRepository userRepository;
    private final UserGroupMapper userGroupMapper;
    private final ValidateGroupForUserGroupMapping validateGroupForUserGroupMapping;

    @Override
    public AllUserGroupMappingsResponseDto getAllUserGroupMappings() {
        AllUserGroupMappingsResponseDto allUserGroupMappingsResponseDto = new AllUserGroupMappingsResponseDto();
        Set<UserGroupMappingResponseDto> userGroups = new HashSet<>();

        userGroupRepository.findAll().forEach(userGroupMapping -> {
            userGroups.add(userGroupMapper.userGroupMappingToUserGroupMappingResponseDto(userGroupMapping));
        });

        allUserGroupMappingsResponseDto.setUserGroups(userGroups);
        return allUserGroupMappingsResponseDto;
    }

    @Override
    public UserGroupMappingResponseDto getUserGroupMappingById(String userGroupStringId) {
        Long userGroupId;
        try {
            userGroupId = Long.parseLong(userGroupStringId);
        } catch (Exception e) {
            log.error("[getUserGroupMappingById] Invalid User-Group Mapping with Id: " + userGroupStringId);
            throw new UserGroupNotFoundException("Invalid User-Group Mapping with Id: " + userGroupStringId);
        }

        Optional<UserGroupMapping> userGroupMappingOptional = userGroupRepository.findById(userGroupId);
        if (userGroupMappingOptional.isPresent()) {
            return userGroupMapper.userGroupMappingToUserGroupMappingResponseDto(userGroupMappingOptional.get());
        }
        log.error("[getUserGroupMappingById] Invalid User-Group Mapping Id provided with Id: " + userGroupId);
        throw new UserGroupNotFoundException("Invalid User-Group Mapping with Id: " + userGroupId);
    }

    @Override
    @Transactional
    public UserGroupMappingResponseDto updateUserGroupMappingById(UserGroupMappingUpdateRequestDto userGroupMappingUpdateRequestDto) {
        Long userGroupId = userGroupMappingUpdateRequestDto.getUserGroupId();

        Optional<UserGroupMapping> userGroupMappingOptional = userGroupRepository.findById(userGroupId);

        if (!userGroupMappingOptional.isPresent()) {
            log.error("[updateUserGroupMappingById] Invalid User-Group Mapping [updateUserGroupMappingById] with Id: " + userGroupId);
            throw new UserGroupNotFoundException("Invalid User-Group Mapping with Id: " + userGroupId);
        } else {
            UserGroupMapping userGroupMapping = userGroupMappingOptional.get();

            Long userId = userGroupMappingUpdateRequestDto.getUserId();
            Long groupId = userGroupMappingUpdateRequestDto.getGroupId();

            /* Check if the same userId and groupId combination already exists*/
            Optional<UserGroupMapping> dtoUserGroupMappingOptional = userGroupRepository
                    .findUserGroupMappingByUserIdAndGroupId(userId, groupId);

            if (dtoUserGroupMappingOptional.isPresent()) {
                UserGroupMapping dtoUserGroupMapping = dtoUserGroupMappingOptional.get();
                if (userGroupMapping.getUserGroupId() != dtoUserGroupMapping.getUserGroupId()) {
                    log.error("[updateUserGroupMappingById] UserId: " + userId + " and GroupId: " + groupId + " lookup value already exist");
                    throw new UserGroupNotUniqueException("UserId: " + userId + " and GroupId: " + groupId + " lookup value already exist");
                }
            }

            /* Check for valid userId */
            if (!validateUserId(userId)) {
                log.error("[updateUserGroupMappingById] Invalid User Id: " + userId);
                throw new UserNotFoundException("Invalid User Id: " + userId);
            }

            /* Check for valid groupId */
            validateGroupForUserGroupMapping.checkGroupExist(groupId);

            userGroupMapping.setUserId(userGroupMappingUpdateRequestDto.getUserId());
            userGroupMapping.setGroupId(userGroupMappingUpdateRequestDto.getGroupId());

            return userGroupMapper.userGroupMappingToUserGroupMappingResponseDto(userGroupRepository.save(userGroupMapping));
        }

    }

    @Override
    @Transactional
    public UserGroupMappingResponseDto createUserGroupMapping(UserGroupMappingRequestDto userGroupMappingRequestDto) {

        Long userId = userGroupMappingRequestDto.getUserId();
        Long groupId = userGroupMappingRequestDto.getGroupId();

        /* Check for valid userId */
        if (!validateUserId(userId)) {
            log.error("[createUserGroupMapping] Invalid User Id: " + userId);
            throw new UserNotFoundException("Invalid User Id: " + userId);
        }

        /* Check for valid groupId */
        validateGroupForUserGroupMapping.checkGroupExist(groupId);

        try {
            return userGroupMapper.userGroupMappingToUserGroupMappingResponseDto(userGroupRepository
                    .save(userGroupMapper.userGroupMappingRequestDtoToUserGroup(userGroupMappingRequestDto)));
        } catch (DataIntegrityViolationException ex) {
            log.error("[createUserGroupMapping] UserId: " + userId + " and GroupId: " + groupId + " lookup value already exist");
            throw new UserGroupNotUniqueException("UserId: " + userId + " and GroupId: " + groupId + " lookup value already exist");
        }
    }


    @Override
    @Transactional
    public UserGroupMappingResponseDto deleteByUserIdAndGroupId(String userName, Long groupId) {
        Optional<User> userOptional = userRepository.findByUserName(userName);
        if (!userOptional.isPresent()) {
            log.error("[deleteByUserIdAndGroupId] Invalid User Name: " + userName);
            throw new UserNotFoundException("Invalid User Name: " + userName);
        }

        User user = userOptional.get();
        Long userId = user.getUserId();

        Optional<UserGroupMapping> userGroupMappingByUserIdAndGroupId = userGroupRepository.findUserGroupMappingByUserIdAndGroupId(userId, groupId);

        if (!userGroupMappingByUserIdAndGroupId.isPresent()) {
            log.error("[deleteByUserIdAndGroupId] Invalid User-Group Mapping with User name: " + userName + " and Group Id: " + groupId);
            throw new UserGroupNotFoundException("Invalid User-Group Mapping with User name: " + userName + " and Group Id: " + groupId);
        }
        userGroupRepository.deleteByUserIdAndGroupId(userId, groupId);
        return userGroupMapper.userGroupMappingToUserGroupMappingResponseDto(userGroupMappingByUserIdAndGroupId.get());
    }

    public boolean validateUserId(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        return (userOptional.isPresent());
    }
}
