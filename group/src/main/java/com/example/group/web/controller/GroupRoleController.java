package com.example.group.web.controller;

import com.example.group.service.GroupRoleService;
import com.example.group.web.model.GroupRoleMappingDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/group-roles")
public class GroupRoleController {

    private final GroupRoleService groupRoleService;

    @GetMapping
    public ResponseEntity<Set<GroupRoleMappingDto>> getGroupRoles(){
        return new ResponseEntity<>(groupRoleService.getGroupRoleMapping(), HttpStatus.OK);
    }

    @GetMapping("/{groupRoleId}")
    public ResponseEntity<GroupRoleMappingDto> getGroupRoleMappingById(@PathVariable Long groupRoleId){
        return new ResponseEntity<>(groupRoleService.getGroupRoleMappingById(groupRoleId), HttpStatus.OK);
    }

    @PutMapping("/{groupRoleId}")
    public ResponseEntity<GroupRoleMappingDto> updateGroupRoleMappingById(@PathVariable Long groupRoleId, @Valid @RequestBody GroupRoleMappingDto groupRoleMappingDto){
        return new ResponseEntity<>(groupRoleService.updateGroupRoleMappingById(groupRoleId ,groupRoleMappingDto), HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity<GroupRoleMappingDto> createGroupRoleMapping(@Valid @RequestBody GroupRoleMappingDto groupRoleMappingDto){
        return new ResponseEntity<>(groupRoleService.createGroupRoleMapping(groupRoleMappingDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{groupRoleId}")
    public ResponseEntity<?> deleteById(@PathVariable Long groupRoleId){
        groupRoleService.deleteById(groupRoleId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
