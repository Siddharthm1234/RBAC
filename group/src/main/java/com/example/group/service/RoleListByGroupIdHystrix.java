package com.example.group.service;

import com.example.group.web.dto.responseDto.GroupRoleMappingResponseDto;
import com.example.group.web.dto.responseDto.RoleResponseDto;
import com.example.group.web.dto.responseDto.RolesList;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashSet;
import java.util.Set;

/**
 * Hystrix Circuit Breaker implementation when fetching Roles for a Group
 *
 * @author Siddharth Mehta
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class RoleListByGroupIdHystrix {

    private final RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "getFallBackRoleListByGroupId",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "2000")
            })
    public ResponseEntity<RolesList> getRoleListByGroupId(Set<GroupRoleMappingResponseDto> groupRoleMappingResponseDtos) {
        ResponseEntity<RolesList> rolesListResponseEntity = restTemplate.postForEntity("http://role-service/roles/group-roles/", groupRoleMappingResponseDtos, RolesList.class);
        log.info("Fetching roleList from getRoleListByGroupId!");
        return rolesListResponseEntity;
    }

    public ResponseEntity<RolesList> getFallBackRoleListByGroupId(Set<GroupRoleMappingResponseDto> groupRoleMappingResponseDtos) {
        RolesList fallBackRolesList = new RolesList();
        Set<RoleResponseDto> roleResponseDtoHashSet = new HashSet<>();

        if (!groupRoleMappingResponseDtos.isEmpty()) {
            groupRoleMappingResponseDtos.forEach(groupRoleMappingDto -> {
                roleResponseDtoHashSet.add(new RoleResponseDto(groupRoleMappingDto.getRoleId(), null, null,
                        "Role Name Unavailable", "Role Description Unavailable"));
            });
        }

        fallBackRolesList.setRoles(roleResponseDtoHashSet);
        log.info("Fetching roleList from getFallBackRoleListByGroupId!");
        return new ResponseEntity<>(fallBackRolesList, HttpStatus.OK);
    }
}
