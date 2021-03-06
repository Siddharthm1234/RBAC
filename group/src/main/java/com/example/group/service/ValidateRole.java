package com.example.group.service;


import com.example.group.restTemplate.RoleRestTemplateResponseErrorHandler;
import com.example.group.web.exception.RoleNotFoundException;
import com.example.group.web.exception.RoleServiceDownException;
import com.example.group.web.model.RoleDto;
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
public class ValidateRole {
    private final RestTemplate restTemplate;
    private final RoleRestTemplateResponseErrorHandler restTemplateResponseErrorHandler;

    void checkRoleExist(Long roleId){

        restTemplate.setErrorHandler(restTemplateResponseErrorHandler);

        try {
            RoleDto roleDto = restTemplate.getForObject("http://role-service/roles/" + roleId, RoleDto.class);
        }
        catch (IllegalStateException e) {
            //Caught when Role Service is Down
            throw new RoleServiceDownException("Role Service Down!");
        }
    }
}
