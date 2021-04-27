package com.example.user.service;


import com.example.user.web.dto.responseDto.*;
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
     * This method is used to fetch set of all groups for a user from Group-Service.
     *
     * @param userGroupMappingResponseDtos Set of all User-Group-Mappings
     * @return ResponseEntity<GroupList>  response entity of groupList.
     */
    @HystrixCommand(fallbackMethod = "getFallBackAllCredentialsListByUserId",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "2000")
            })
    public ResponseEntity<AllCredentialList> getAllCredentialListByUserId(Set<UserGroupMappingResponseDto> userGroupMappingResponseDtos) {
        ResponseEntity<AllCredentialList> allCredentialListResponseEntity = restTemplate.postForEntity("http://group-service/groups/all-credentials/", userGroupMappingResponseDtos, AllCredentialList.class);
        return allCredentialListResponseEntity;
    }

    /**
     * This is a fallback method used to return the user with group ids
     * only since Group-Service is down or timed out. Roles are also set to null.
     *
     * @param userGroupMappingResponseDtos Set of all User-Group-Mappings
     * @return ResponseEntity<AllCredentialList>  response entity of allCredentialList.
     */
    public ResponseEntity<AllCredentialList> getFallBackAllCredentialsListByUserId(Set<UserGroupMappingResponseDto> userGroupMappingResponseDtos) {
        AllCredentialList fallbackAllCredentialList = new AllCredentialList();
        Set<GroupResponseDto> groupResponseDtoHashSet = new HashSet<>();
        Set<RoleResponseDto> roleResponseDtoHashSet = new HashSet<>();


        if (!userGroupMappingResponseDtos.isEmpty()) {
            userGroupMappingResponseDtos.forEach(userGroupMappingDto -> {
                groupResponseDtoHashSet.add(new GroupResponseDto(userGroupMappingDto.getGroupId(), null, null,
                        "Group Name Unavailable", "Group Description Unavailable"));
            });
        }
        roleResponseDtoHashSet.add(new RoleResponseDto(null, null, null, null, null));

        fallbackAllCredentialList.setGroups(groupResponseDtoHashSet);
        fallbackAllCredentialList.setRoles(roleResponseDtoHashSet);
        return new ResponseEntity<>(fallbackAllCredentialList, HttpStatus.OK);
    }
}
