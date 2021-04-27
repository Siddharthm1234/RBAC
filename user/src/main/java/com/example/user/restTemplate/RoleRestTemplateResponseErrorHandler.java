package com.example.user.restTemplate;

import com.example.user.web.exception.GroupServiceDownException;
import com.example.user.web.exception.RoleNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

import static org.springframework.http.HttpStatus.Series.SERVER_ERROR;

/**
 * Custom Response Handler for RestTemplate
 *
 * @author Siddharth Mehta
 */
@Component
@Slf4j
public class RoleRestTemplateResponseErrorHandler implements ResponseErrorHandler {
    @Override
    public boolean hasError(ClientHttpResponse httpResponse) throws IOException {
        return (httpResponse.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR
                || httpResponse.getStatusCode().series() == SERVER_ERROR);
    }

    @Override
    public void handleError(ClientHttpResponse httpResponse) throws IOException {

        if (httpResponse.getStatusCode()
                .series() == HttpStatus.Series.SERVER_ERROR) {
            log.error("Group Service Down!");
            throw new GroupServiceDownException("Group Service Down!");
        } else if (httpResponse.getStatusCode()
                .series() == HttpStatus.Series.CLIENT_ERROR) {
            if (httpResponse.getStatusCode() == HttpStatus.NOT_FOUND) {
                log.error("Invalid Role Id");
                throw new RoleNotFoundException("Invalid Role Id");
            }
        }
    }
}
