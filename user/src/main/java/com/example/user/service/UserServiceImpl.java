package com.example.user.service;

import com.example.user.domain.User;
import com.example.user.domain.UserGroupMapping;
import com.example.user.repository.UserGroupRepository;
import com.example.user.repository.UserRepository;
import com.example.user.web.dto.responseDto.*;
import com.example.user.web.exception.UserNameNotUniqueException;
import com.example.user.web.exception.UserNotFoundException;
import com.example.user.web.mapper.UserGroupMapper;
import com.example.user.web.mapper.UserMapper;
import com.example.user.web.dto.requestDto.UserRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Implementation for User Service
 *
 * @author Siddharth Mehta
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserGroupRepository userGroupRepository;
    private final UserGroupMapper userGroupMapper;
    private final UserMapper userMapper;
    private final GroupListByUserIdHystrix groupListByUserIdHystrix;
    private final AllCredentialsByUserIdHystrix allCredentialsByUserIdHystrix;
    private final ValidateRoleForUserId validateRoleForUserId;

    @Override
    public AllUsersResponseDto getAllUsers() {
        AllUsersResponseDto allUsersResponseDto = new AllUsersResponseDto();
        Set<UserResponseDto> users = new HashSet<>();

        userRepository.findAll().forEach(user -> {
            users.add(userMapper.userToUserResponseDto(user));
        });

        allUsersResponseDto.setUsers(users);

        return allUsersResponseDto;
    }

    @Override
    public UserResponseDto getUserByName(String userName) {
        User user = getUserByUserName(userName);
        return userMapper.userToUserResponseDto(user);
    }

    @Override
    @Transactional
    public UserResponseDto updateUserByName(UserRequestDto userRequestDto) {
        String dtoUserName = userRequestDto.getUserName();
        Optional<User> userOptional = userRepository.findByUserName(dtoUserName);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            user.setUserName(userRequestDto.getUserName());
            user.setFirstName(userRequestDto.getFirstName());
            user.setLastName(userRequestDto.getLastName());

            return userMapper.userToUserResponseDto(userRepository.save(user));

        }

        log.error("[updateUserByName] Invalid User Name with name: " + dtoUserName);
        throw new UserNotFoundException("Invalid User with name: " + dtoUserName);
    }

    @Override
    @Transactional
    public UserResponseDto createUser(UserRequestDto userRequestDto) {

        try {
            return userMapper.userToUserResponseDto(userRepository.save(userMapper.userRequestDtoToUser(userRequestDto)));
        } catch (DataIntegrityViolationException ex) {
            log.error("[createUser] User by the name " + userRequestDto.getUserName() + " already exists!");
            throw new UserNameNotUniqueException("User by the name " + userRequestDto.getUserName() + " already exists!");
        }

    }

    @Override
    @Transactional
    public UserResponseDto deleteByName(String userName) {
        if (userName == null || userName.isEmpty()) {
            log.error("[deleteByName] UserName cannot be null");
            throw new UserNotFoundException("UserName cannot be null");
        }

        Optional<User> userOptional = userRepository.findByUserName(userName);

        if (!userOptional.isPresent()) {
            log.error("[deleteByName] Invalid User Name: " + userName);
            throw new UserNotFoundException("Invalid User Name: " + userName);
        }

        Long userId = userOptional.get().getUserId();
        userRepository.deleteByUserName(userName);

        /*Delete all User-Group Mappings for the deleted userId */
        userGroupRepository.deleteByUserId(userId);

        return userMapper.userToUserResponseDto(userOptional.get());
    }

    /*----------------- Groups from User Name -------------------*/

    /**
     * This method is used to get a list of groups for a user.
     *
     * @param userName Name of user
     * @return GroupList object holding user and corresponding groups.
     */
    @Override
    @Transactional
    public GroupsList getGroupsByUserName(String userName) {
        User user = this.getUserByUserName(userName);
        Long userId = user.getUserId();
        Set<UserGroupMappingResponseDto> userGroupMappingResponseDtos = new HashSet<>();

        userGroupRepository.findByUserId(userId).forEach(userGroupMapping -> {
            userGroupMappingResponseDtos.add(userGroupMapper.userGroupMappingToUserGroupMappingResponseDto(userGroupMapping));
        });

        ResponseEntity<GroupsList> groupsListResponseEntity = groupListByUserIdHystrix.getGroupListByUserId(userGroupMappingResponseDtos);
        GroupsList groupsList = groupsListResponseEntity.getBody();
        groupsList.setUser(userMapper.userToUserResponseDto(user));
        return groupsList;
    }

    /*-------------- Check if a User belongs to Group ---------------*/

    /**
     * This method is used to check if a user belongs to group.
     *
     * @param userName Name of user
     * @param groupId  Id of group
     * @return String user belongs to group,
     * user doesn't belong to group
     */
    @Override
    public String checkGroupIdForUserName(String userName, Long groupId) {
        User user = getUserByUserName(userName);
        Long userId = user.getUserId();

        Optional<UserGroupMapping> userGroupMappingOptional = userGroupRepository
                .findUserGroupMappingByUserIdAndGroupId(userId, groupId);

        return (userGroupMappingOptional.isPresent())
                ? "User " + userName + " Belongs To Group " + groupId + "!"
                : "User " + userName + " Doesn't Belongs To Group " + groupId + "!";

    }

    /*-------------- Check if a User has a Role ---------------*/

    /**
     * This method is used to check if a user has role permission.
     *
     * @param userName Name of user
     * @param roleId   Id of role
     * @return Boolean True (user has role permission),
     * False (user doesn't have role permission)
     */
    @Override
    public String checkRoleIdForUserName(String userName, Long roleId) {

        User user = getUserByUserName(userName);
        Long userId = user.getUserId();

        return (validateRoleForUserId.checkRolePermissionExistForUser(userId, roleId))
                ? "User " + userName + " Has Role " + roleId + " Permission!"
                : "User " + userName + " Doesn't Have Role " + roleId + " Permission!";
    }

    /**
     * This method is used to get a list of groups and roles for a user.
     *
     * @param userName Name of user
     * @return AllCredentialList object holding user and corresponding groups and roles.
     */
    @Override
    public AllCredentialList getGroupsAndRolesByUserName(String userName) {
        User user = this.getUserByUserName(userName);
        Long userId = user.getUserId();
        Set<UserGroupMappingResponseDto> userGroupMappingResponseDtos = new HashSet<>();

        userGroupRepository.findByUserId(userId).forEach(userGroupMapping -> {
            userGroupMappingResponseDtos.add(userGroupMapper.userGroupMappingToUserGroupMappingResponseDto(userGroupMapping));
        });

        ResponseEntity<AllCredentialList> allCredentialListResponseEntity = allCredentialsByUserIdHystrix.getAllCredentialListByUserId(userGroupMappingResponseDtos);
        AllCredentialList allCredentialList = allCredentialListResponseEntity.getBody();
        allCredentialList.setUser(userMapper.userToUserResponseDto(user));
        return allCredentialList;
    }

    //Helper function
    User getUserByUserName(String userName) {
        Optional<User> userOptional = userRepository.findByUserName(userName);

        if (!userOptional.isPresent()) {
            log.error("[getUserByUserName] Invalid User Name: " + userName);
            throw new UserNotFoundException("Invalid User Name: " + userName);
        }

        return userOptional.get();
    }

}
