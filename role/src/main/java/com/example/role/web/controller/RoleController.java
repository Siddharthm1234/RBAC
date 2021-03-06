package com.example.role.web.controller;


import com.example.role.service.RoleService;
import com.example.role.web.model.GroupRoleMappingDto;
import com.example.role.web.model.RoleDto;
import com.example.role.web.model.RolesList;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@Controller
@RequiredArgsConstructor
@RequestMapping("/roles")
public class RoleController {

    private final RoleService roleService;

    @GetMapping
    public ResponseEntity<Set<RoleDto>> getRoles(){
        return new ResponseEntity<>(roleService.getRoles(), HttpStatus.OK);
    }

    @GetMapping("/{roleId}")
    public ResponseEntity<RoleDto> getRoleById(@PathVariable Long roleId){
        return new ResponseEntity<>(roleService.getRoleById(roleId), HttpStatus.OK);
    }

    @PutMapping("/{roleId}")
    public ResponseEntity<RoleDto> updateRoleById(@PathVariable Long roleId, @Valid @RequestBody RoleDto roleDto){
        return new ResponseEntity<>(roleService.updateRoleById(roleId ,roleDto), HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity<RoleDto> createRole(@Valid @RequestBody RoleDto roleDto){
        return new ResponseEntity<>(roleService.createRole(roleDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{roleId}")
    public ResponseEntity<?> deleteById(@PathVariable Long roleId){
        roleService.deleteById(roleId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PostMapping("/group-roles")
    public ResponseEntity<RolesList> getRolesByGroupId(@RequestBody Set<GroupRoleMappingDto> groupRoleMappingDtos){
        Set<RoleDto> roleDtoSet = new HashSet<>();

        groupRoleMappingDtos.forEach(groupRoleMappingDto -> {
            roleDtoSet.add( roleService.getRoleById(groupRoleMappingDto.getRoleId()));
        });

        RolesList rolesList =new RolesList();
        rolesList.setRolesSet(roleDtoSet);
        return new ResponseEntity<>(rolesList, HttpStatus.OK);
    }
}
