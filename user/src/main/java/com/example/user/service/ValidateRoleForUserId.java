package com.example.user.service;

import com.example.user.restTemplate.RoleRestTemplateResponseErrorHandler;
import com.example.user.web.exception.GroupServiceDownException;
import com.example.user.web.exception.RoleNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

import static org.springframework.http.HttpStatus.Series.SERVER_ERROR;

@Service
@RequiredArgsConstructor
public class ValidateRoleForUserId {

    private final RestTemplate restTemplate;
    private final RoleRestTemplateResponseErrorHandler roleRestTemplateResponseErrorHandler;

    Boolean checkRolePermissionExistForUser(Long userId, Long roleId){

        restTemplate.setErrorHandler(roleRestTemplateResponseErrorHandler);


        try {
            return restTemplate.getForObject("http://group-service/groups/userId/" + userId + "/roleId/" + roleId + "/check",
                    Boolean.class);
        }
        catch (IllegalStateException e) {
            //Caught when Group Service is Down - (No way to check Group Validity - pass True)
            throw new GroupServiceDownException("Group Service Down!");
        }
    }
}
