package com.example.user.service;


import com.example.user.web.model.GroupDto;
import com.example.user.web.model.GroupsList;
import com.example.user.web.model.UserGroupMappingDto;
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
public class GroupListByUserIdHystrix {

    private final RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "getFallBackGroupListByUserId")
    public ResponseEntity<GroupsList> getGroupListByUserId(Set<UserGroupMappingDto> userGroupMappingDtos){
        ResponseEntity<GroupsList> groupsListResponseEntity = restTemplate.postForEntity("http://group-service/groups/user-groups/", userGroupMappingDtos, GroupsList.class);
        return groupsListResponseEntity;
    }

    public ResponseEntity<GroupsList> getFallBackGroupListByUserId(Set<UserGroupMappingDto> userGroupMappingDtos){
        GroupsList fallBackGroupList = new GroupsList();
        Set<GroupDto> groupDtoHashSet = new HashSet<>();

        if(!userGroupMappingDtos.isEmpty()){
            userGroupMappingDtos.forEach(userGroupMappingDto -> {
                groupDtoHashSet.add(new GroupDto(userGroupMappingDto.getGroupId(), null, null,
                        "Group Name Unavailable", "Group Description Unavailable"));
            });
        }

        fallBackGroupList.setGroupDtoSet(groupDtoHashSet);
        return new ResponseEntity<>(fallBackGroupList, HttpStatus.OK);
    }
}
