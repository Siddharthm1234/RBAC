package com.example.user.service;

import com.example.user.restTemplate.GroupRestTemplateResponseErrorHandler;
import com.example.user.web.exception.GroupServiceDownException;
import com.example.user.web.model.GroupDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class ValidateGroupForUserGroupMapping {
    private final RestTemplate restTemplate;
    private final GroupRestTemplateResponseErrorHandler groupRestTemplateResponseErrorHandler;

    void checkGroupExist(Long groupId) {

        restTemplate.setErrorHandler(groupRestTemplateResponseErrorHandler);

        try {
            GroupDto groupDto = restTemplate.getForObject("http://group-service/groups/" + groupId, GroupDto.class);
        } catch (IllegalStateException e) {
            //Caught when Group Service is Down
            throw new GroupServiceDownException("Group Service Down!");
        }
    }
}
