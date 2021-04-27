package com.example.group.web.controller;

import com.example.group.service.GroupRoleService;
import com.example.group.web.dto.ResponseDto;
import com.example.group.web.dto.requestDto.GroupRoleMappingRequestDto;
import com.example.group.web.dto.requestDto.GroupRoleMappingUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Exposes all GroupRole - RESTful web services
 *
 * @author Siddharth Mehta
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/group-roles")
public class GroupRoleController {

    private final GroupRoleService groupRoleService;

    @GetMapping
    public ResponseEntity<ResponseDto> getAllGroupRoles() {
        return new ResponseEntity<>(new ResponseDto(groupRoleService.getAllGroupRoleMappings(), null), HttpStatus.OK);
    }

    @GetMapping("/{groupRoleId}")
    public ResponseEntity<ResponseDto> getGroupRoleMappingById(@PathVariable Long groupRoleId) {
        return new ResponseEntity<>(new ResponseDto(groupRoleService.getGroupRoleMappingById(groupRoleId), null), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<ResponseDto> updateGroupRoleMappingById(@Valid @RequestBody GroupRoleMappingUpdateRequestDto groupRoleMappingUpdateRequestDto) {
        return new ResponseEntity<>(new ResponseDto(groupRoleService.updateGroupRoleMappingById(groupRoleMappingUpdateRequestDto), null), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseDto> createGroupRoleMapping(@Valid @RequestBody GroupRoleMappingRequestDto groupRoleMappingRequestDto) {
        return new ResponseEntity<>(new ResponseDto(groupRoleService.createGroupRoleMapping(groupRoleMappingRequestDto), null), HttpStatus.CREATED);
    }

    @DeleteMapping("/group/{groupId}/role/{roleId}")
    public ResponseEntity<ResponseDto> deleteById(@PathVariable Long groupId, @PathVariable Long roleId) {
        return new ResponseEntity<>(new ResponseDto(groupRoleService.deleteByGroupIdAndRoleId(groupId, roleId), null), HttpStatus.OK);
    }

}
