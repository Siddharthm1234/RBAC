package com.example.user.service;

import com.example.user.restTemplate.RoleRestTemplateResponseErrorHandler;
import com.example.user.web.exception.GroupServiceDownException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;


@Service
@Slf4j
@RequiredArgsConstructor
public class ValidateRoleForUserId {

    private final RestTemplate restTemplate;
    private final RoleRestTemplateResponseErrorHandler roleRestTemplateResponseErrorHandler;

    Boolean checkRolePermissionExistForUser(Long userId, Long roleId) {

        restTemplate.setErrorHandler(roleRestTemplateResponseErrorHandler);

        try {
            return restTemplate.getForObject("http://group-service/groups/userId/" + userId + "/roleId/" + roleId + "/check",
                    Boolean.class);
        } catch (IllegalStateException illegalStateException) {
            log.error("[checkRolePermissionExistForUser] Group Service Down!");
            throw new GroupServiceDownException("Group Service Down!");
        } catch (RestClientException restClientException) {
            log.error("[checkRolePermissionExistForUser] Group Service acting poorly, timed out!");
            throw new GroupServiceDownException("Group Service acting poorly, timed out!");
        } catch (Exception exception) {
            log.error("[checkGroupExist] Role does not exist!");
            throw new GroupServiceDownException("Invalid Role Id: " + roleId);
        }
    }
}
