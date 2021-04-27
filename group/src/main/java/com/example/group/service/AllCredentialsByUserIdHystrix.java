package com.example.group.service;


import com.example.group.web.dto.responseDto.AllCredentialList;
import com.example.group.web.dto.responseDto.GroupRoleMappingResponseDto;
import com.example.group.web.dto.responseDto.RoleResponseDto;
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
 * Hystrix Circuit Breaker implementation when fetching Groups for a User
 *
 * @author Siddharth Mehta
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class AllCredentialsByUserIdHystrix {

    private final RestTemplate restTemplate;

    /**
     * This method is used to fetch set of all roles for a user from Role-Service.
     *
     * @param groupRoleMappingResponseDtos Set of all Group-Role-Mappings
     * @return ResponseEntity<GroupList>  response entity of groupList.
     */
    @HystrixCommand(fallbackMethod = "getFallBackAllCredentialsListByUserId")
    public ResponseEntity<AllCredentialList> getAllCredentialListByUserId(Set<GroupRoleMappingResponseDto> groupRoleMappingResponseDtos) {
        ResponseEntity<AllCredentialList> allCredentialListResponseEntity = restTemplate.postForEntity("http://role-service/roles/group-roles", groupRoleMappingResponseDtos, AllCredentialList.class);
        return allCredentialListResponseEntity;
    }

    /**
     * This is a fallback method used to return the user with role ids
     * only since Role-Service is down or timed out.
     *
     * @param groupRoleMappingResponseDtos Set of all User-Group-Mappings
     * @return ResponseEntity<AllCredentialList>  response entity of allCredentialList.
     */
    public ResponseEntity<AllCredentialList> getFallBackAllCredentialsListByUserId(Set<GroupRoleMappingResponseDto> groupRoleMappingResponseDtos) {
        AllCredentialList fallbackAllCredentialList = new AllCredentialList();
        Set<RoleResponseDto> roleResponseDtoHashSet = new HashSet<>();

        if (!groupRoleMappingResponseDtos.isEmpty()) {
            groupRoleMappingResponseDtos.forEach(groupRoleMappingDto -> {
                roleResponseDtoHashSet.add(new RoleResponseDto(groupRoleMappingDto.getRoleId(), null, null,
                        "Role Name Unavailable", "Role Description Unavailable"));
            });
        }

        fallbackAllCredentialList.setRoles(roleResponseDtoHashSet);
        return new ResponseEntity<>(fallbackAllCredentialList, HttpStatus.OK);
    }
}
