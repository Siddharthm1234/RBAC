package com.example.user.service;

import com.example.user.restTemplate.GroupRestTemplateResponseErrorHandler;
import com.example.user.web.model.GroupDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.*;

@Service
@RequiredArgsConstructor
public class ValidateGroupForUserId {

    private final RestTemplate restTemplate;
    private final GroupRestTemplateResponseErrorHandler groupRestTemplateResponseErrorHandler;

    Boolean checkGroupExist(Long groupId){

        restTemplate.setErrorHandler(groupRestTemplateResponseErrorHandler);

        GroupDto groupDto =null;
        try {
            groupDto = restTemplate.getForObject("http://group-service/groups/" + groupId, GroupDto.class);
        }
        catch (IllegalStateException e) {
            //Caught when Group Service is Down - (No way to check Group Validity - pass True)
            return checkFallbackGroupExist();
        }
        return (!(groupDto==null));
    }

    Boolean checkFallbackGroupExist(){
        return true;
    }
}
