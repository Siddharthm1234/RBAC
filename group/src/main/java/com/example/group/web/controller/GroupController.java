package com.example.group.web.controller;

import com.example.group.service.GroupService;
import com.example.group.service.UserGroupService;
import com.example.group.web.model.GroupDto;
import com.example.group.web.model.GroupsList;
import com.example.group.web.model.RolesList;
import com.example.group.web.model.UserGroupMappingDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/groups")
public class GroupController {

    private final GroupService groupService;
    private final UserGroupService userGroupService;

    @GetMapping
    public ResponseEntity<Set<GroupDto>> getGroups(){
        return new ResponseEntity<>(groupService.getGroups(), HttpStatus.OK);
    }

    @GetMapping("/{groupId}")
    public ResponseEntity<GroupDto> getGroupById(@PathVariable Long groupId){
        return new ResponseEntity<>(groupService.getGroupById(groupId), HttpStatus.OK);
    }

    @PutMapping("/{groupId}")
    public ResponseEntity<GroupDto> updateGroupById(@PathVariable Long groupId, @Valid @RequestBody GroupDto groupDto){
        return new ResponseEntity<>(groupService.updateGroupById(groupId ,groupDto), HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity<GroupDto> createGroup(@Valid @RequestBody GroupDto groupDto){
        return new ResponseEntity<>(groupService.createGroup(groupDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{groupId}")
    public ResponseEntity<?> deleteById(@PathVariable Long groupId){
        groupService.deleteById(groupId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    /*----------------- Roles from Group Id -------------------*/
    @GetMapping("/{groupId}/roles")
    public ResponseEntity<RolesList> getRolesByGroupId(@PathVariable Long groupId){
        return new ResponseEntity<>(groupService.getRolesByGroupId(groupId), HttpStatus.OK);
    }

    /*----------------- Groups from User Name -------------------*/
    @PostMapping("/user-groups")
    public ResponseEntity<GroupsList> getGroupsByUserId(@RequestBody Set<UserGroupMappingDto> userGroupMappingDtos){
        GroupsList groupsList =new GroupsList();
        groupsList.setGroupDtoSet(groupService.getGroupsByUserId(userGroupMappingDtos));
        return new ResponseEntity<>(groupsList, HttpStatus.OK);
    }

    /*-------------- Check if a User has a Role ---------------*/
    @GetMapping("/userId/{userId}/roleId/{roleId}/check")
    public Boolean checkRoleIdForUserId(@PathVariable Long userId, @PathVariable Long roleId){
        Set<Long> groupIds = userGroupService.getGroupIdsForUserId(userId);
        return userGroupService.getRoleIdsForGroupIds(groupIds, roleId);
    }

}
