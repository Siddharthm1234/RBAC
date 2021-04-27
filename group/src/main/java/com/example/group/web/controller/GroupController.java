package com.example.group.web.controller;

import com.example.group.service.GroupService;
import com.example.group.service.UserGroupService;
import com.example.group.web.dto.ResponseDto;
import com.example.group.web.dto.requestDto.GroupRequestDto;
import com.example.group.web.dto.requestDto.GroupUpdateRequestDto;
import com.example.group.web.dto.responseDto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Set;

/**
 * Exposes all Group - RESTful web services
 *
 * @author Siddharth Mehta
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/groups")
public class GroupController {

    private final GroupService groupService;
    private final UserGroupService userGroupService;

    @GetMapping
    public ResponseEntity<ResponseDto> getAllGroups() {
        return new ResponseEntity<>(new ResponseDto(groupService.getAllGroups(), null), HttpStatus.OK);
    }

    @GetMapping("/{groupId}")
    public ResponseEntity<ResponseDto> getGroupById(@PathVariable Long groupId) {
        return new ResponseEntity<>(new ResponseDto(groupService.getGroupById(groupId), null), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<ResponseDto> updateGroupById(@Valid @RequestBody GroupUpdateRequestDto groupUpdateRequestDto) {
        return new ResponseEntity<>(new ResponseDto(groupService.updateGroupById(groupUpdateRequestDto), null), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseDto> createGroup(@Valid @RequestBody GroupRequestDto groupRequestDto) {
        return new ResponseEntity<>(new ResponseDto(groupService.createGroup(groupRequestDto), null), HttpStatus.CREATED);
    }

    @Transactional
    @DeleteMapping("/{groupId}")
    public ResponseEntity<ResponseDto> deleteById(@PathVariable Long groupId) {
        return new ResponseEntity<>(new ResponseDto(groupService.deleteById(groupId), null), HttpStatus.OK);
    }


    /*----------------- Roles from Group Id -------------------*/

    /**
     * This method is an API endpoint used to get a list of roles for a group.
     *
     * @param groupId Id of Group
     * @return ResponseEntity<ResponseDto>  response entity of global wrapper of all responses.
     */
    @GetMapping("/{groupId}/roles")
    public ResponseEntity<ResponseDto> getRolesByGroupId(@PathVariable Long groupId) {
        return new ResponseEntity<>(new ResponseDto(groupService.getRolesByGroupId(groupId), null), HttpStatus.OK);
    }

    /*----------------- Groups from User Name -------------------*/

    /**
     * This method is an API endpoint used to fulfill request of User-Service to fetch all Groups for User.
     *
     * @param Set<UserGroupMappingResponseDto> Set of UserGroupMappingResponseDto
     * @return ResponseEntity<GroupList> object holding user and corresponding groups.
     */
    @PostMapping("/user-groups")
    public ResponseEntity<GroupsList> getGroupsByUserId(@RequestBody Set<UserGroupMappingResponseDto> userGroupMappingResponseDtos) throws InterruptedException {
        GroupsList groupsList = new GroupsList();

        /*To demonstrate timeouts
        Thread.sleep(3000);*/

        groupsList.setGroups(groupService.getGroupsByUserId(userGroupMappingResponseDtos));
        return new ResponseEntity<>(groupsList, HttpStatus.OK);
    }

    /*-------------- Check if a User has a Role ---------------*/

    /**
     * This method is an API endpoint used to fulfill request of User-Service to check if a User has a Role permission .
     *
     * @param userId Id of User
     * @param roleId Id of Role
     * @return Boolean True (user has role permission),
     * False (user doesn't have role permission).
     */
    @GetMapping("/userId/{userId}/roleId/{roleId}/check")
    public Boolean checkRoleIdForUserId(@PathVariable Long userId, @PathVariable Long roleId) throws InterruptedException {
        Set<Long> groupIds = userGroupService.getGroupIdsForUserId(userId);

        /*To demonstrate timeouts
        Thread.sleep(3000);*/

        return userGroupService.getRoleIdsForGroupIds(groupIds, roleId);
    }


    @GetMapping("/get/{groupId}")
    public ResponseDto getGroupByIdWithReturnResponseDto(@PathVariable Long groupId) {
        return new ResponseDto(groupService.getGroupById(groupId), null);
    }


    /*----------------- Groups And Roles from User Name -------------------*/

    /**
     * This method is an API endpoint used to fulfill request of User-Service to fetch all Groups and Roles for User.
     *
     * @param Set<UserGroupMappingResponseDto> Set of UserGroupMappingResponseDto
     * @return ResponseEntity<AllCredentialList> object holding all credentials.
     */
    @PostMapping("/all-credentials")
    public ResponseEntity<AllCredentialList> getGroupsAndRolesByUserId(@RequestBody Set<UserGroupMappingResponseDto> userGroupMappingResponseDtos) {
        AllCredentialList allCredentialList = groupService.getGroupsAndRolesByUserId(userGroupMappingResponseDtos);
        return new ResponseEntity<>(allCredentialList, HttpStatus.OK);
    }

}
