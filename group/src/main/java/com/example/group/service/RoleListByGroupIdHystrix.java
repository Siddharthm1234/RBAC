package com.example.group.service;

import com.example.group.repository.GroupRoleRepository;
import com.example.group.web.model.GroupRoleMappingDto;
import com.example.group.web.model.RoleDto;
import com.example.group.web.model.RolesList;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RoleListByGroupIdHystrix {

    private final RestTemplate restTemplate;
    private final GroupRoleRepository groupRoleRepository;

    @HystrixCommand(fallbackMethod = "getFallBackRoleListByGroupId")
    public ResponseEntity<RolesList> getRoleListByGroupId(Set<GroupRoleMappingDto> groupRoleMappingDtos){
        ResponseEntity<RolesList> rolesListResponseEntity = restTemplate.postForEntity("http://role-service/roles/group-roles/", groupRoleMappingDtos, RolesList.class);
        return rolesListResponseEntity;
    }

    public ResponseEntity<RolesList> getFallBackRoleListByGroupId(Set<GroupRoleMappingDto> groupRoleMappingDtos){
        RolesList fallBackRolesList = new RolesList();
        Set<RoleDto> roleDtoHashSet = new HashSet<>();

        if(!groupRoleMappingDtos.isEmpty()){
            groupRoleMappingDtos.forEach(groupRoleMappingDto -> {
                roleDtoHashSet.add(new RoleDto(groupRoleMappingDto.getRoleId(), null, null,
                        "Role Name Unavailable", "Role Description Unavailable"));
            });
        }

        fallBackRolesList.setRolesSet(roleDtoHashSet);
        return new ResponseEntity<>(fallBackRolesList, HttpStatus.OK);
    }
}
