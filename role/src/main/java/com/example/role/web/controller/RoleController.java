package com.example.role.web.controller;


import com.example.role.service.RoleService;
import com.example.role.web.dto.ResponseDto;
import com.example.role.web.dto.requestDto.RoleRequestDto;
import com.example.role.web.dto.requestDto.RoleUpdateRequestDto;
import com.example.role.web.dto.responseDto.AllCredentialList;
import com.example.role.web.dto.responseDto.GroupRoleMappingResponseDto;
import com.example.role.web.dto.responseDto.RoleResponseDto;
import com.example.role.web.dto.responseDto.RolesList;
import com.example.role.web.exception.ExceptionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Set;

/**
 * Exposes all Role - RESTful web services
 *
 * @author Siddharth Mehta
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/roles")
public class RoleController {

    private final RoleService roleService;

    @GetMapping
    public ResponseEntity<ResponseDto> getAllRoles() {
        return new ResponseEntity<>(new ResponseDto(roleService.getAllRoles(), null), HttpStatus.OK);
    }

    @GetMapping("/{roleId}")
    public ResponseEntity<ResponseDto> getRoleById(@PathVariable Long roleId) {
        return new ResponseEntity<>(new ResponseDto(roleService.getRoleById(roleId), null), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<ResponseDto> updateRoleById(@Valid @RequestBody RoleUpdateRequestDto roleUpdateRequestDto) {
        return new ResponseEntity<>(new ResponseDto(roleService.updateRoleById(roleUpdateRequestDto), null), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseDto> createRole(@Valid @RequestBody RoleRequestDto roleRequestDto) {
        return new ResponseEntity<>(new ResponseDto(roleService.createRole(roleRequestDto), null), HttpStatus.CREATED);
    }

    @Transactional
    @DeleteMapping("/{roleId}")
    public ResponseEntity<ResponseDto> deleteById(@PathVariable Long roleId) {
        return new ResponseEntity<>(new ResponseDto(roleService.deleteById(roleId), null), HttpStatus.OK);
    }

    /*----------------- Roles from Group Id -------------------*/

    /**
     * This method is an API endpoint used to fulfill request of Group-Service to fetch all Roles for Group.
     *
     * @param Set<GroupRoleMappingResponseDto> Set of GroupRoleMappingResponseDto.
     * @return ResponseEntity<RolesList> object holding group and corresponding roles.
     */
    @PostMapping("/group-roles")
    public ResponseEntity<RolesList> getRolesByGroupId(@RequestBody Set<GroupRoleMappingResponseDto> groupRoleMappingResponseDtos) throws InterruptedException {
        RolesList rolesList = new RolesList();

        /*To demonstrate timeouts
        Thread.sleep(3000);*/

        rolesList.setRoles(roleService.getRolesByGroupId(groupRoleMappingResponseDtos));
        return new ResponseEntity<>(rolesList, HttpStatus.OK);
    }

    @GetMapping("/get/{roleId}")
    public ResponseDto getRoleWithReturnResponseDto(@PathVariable Long roleId) {
        return new ResponseDto(roleService.getRoleById(roleId), null);
    }
}
