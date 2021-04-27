package com.example.user.web.controller;

import com.example.user.service.UserGroupService;
import com.example.user.web.dto.ResponseDto;
import com.example.user.web.dto.requestDto.UserGroupMappingRequestDto;
import com.example.user.web.dto.requestDto.UserGroupMappingUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Exposes all UserGroup - RESTful web services
 *
 * @author Siddharth Mehta
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/user-groups")
public class UserGroupController {

    private final UserGroupService userGroupService;

    @GetMapping
    public ResponseEntity<ResponseDto> getAllUserGroups() {
        return new ResponseEntity<>(new ResponseDto(userGroupService.getAllUserGroupMappings(), null), HttpStatus.OK);
    }

    @GetMapping("/{userGroupStringId}")
    public ResponseEntity<ResponseDto> getUserGroupMappingById(@PathVariable String userGroupStringId) {
        return new ResponseEntity<>(new ResponseDto(userGroupService.getUserGroupMappingById(userGroupStringId), null), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<ResponseDto> updateUserGroupMappingById(@Valid @RequestBody UserGroupMappingUpdateRequestDto userGroupMappingUpdateRequestDto) {
        return new ResponseEntity<>(new ResponseDto(userGroupService.updateUserGroupMappingById(userGroupMappingUpdateRequestDto), null), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseDto> createUserGroupMapping(@Valid @RequestBody UserGroupMappingRequestDto userGroupMappingRequestDto) {
        return new ResponseEntity<>(new ResponseDto(userGroupService.createUserGroupMapping(userGroupMappingRequestDto), null), HttpStatus.CREATED);
    }

    @DeleteMapping("/user/{userName}/group/{groupId}")
    public ResponseEntity<ResponseDto> deleteById(@PathVariable String userName, @PathVariable Long groupId) {
        return new ResponseEntity<>(new ResponseDto(userGroupService.deleteByUserIdAndGroupId(userName, groupId), null), HttpStatus.OK);
    }
}
