package com.example.group.service;


import com.example.group.restTemplate.RoleRestTemplateResponseErrorHandler;
import com.example.group.web.dto.ResponseDto;
import com.example.group.web.exception.RoleNotFoundException;
import com.example.group.web.exception.RoleServiceDownException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;


@Service
@Slf4j
@RequiredArgsConstructor
public class ValidateRoleForGroupRoleMapping {
    private final RestTemplate restTemplate;
    private final RoleRestTemplateResponseErrorHandler roleRestTemplateResponseErrorHandler;

    void checkRoleExist(Long roleId) {

        restTemplate.setErrorHandler(roleRestTemplateResponseErrorHandler);

        try {
            ResponseDto responseDto = restTemplate.getForObject("http://role-service/roles/get/" + roleId,
                    ResponseDto.class);
            System.out.println(responseDto.toString());
            if (responseDto.getError() != null) {
                log.error("[checkRoleExist] Role does not exist!");
                throw new RoleNotFoundException("Invalid Role Id: " + roleId);
            }
        } catch (IllegalStateException illegalStateException) {
            log.error("[checkRoleExist] Role Service Down!");
            throw new RoleServiceDownException("Role Service Down!");
        } /*catch (RestClientException restClientException) {
            log.error("[checkRoleExist] Role Service acting poorly, timed out!");
            throw new RoleServiceDownException("Role Service acting poorly, timed out!");
        }*/
    }
}
