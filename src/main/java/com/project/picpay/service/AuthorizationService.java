package com.project.picpay.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthorizationService {
    private final RestTemplate restTemplate;

    public boolean authorizeTransaction() {
        var response = restTemplate.getForEntity("https://util.devi.tools/api/v2/authorize", Map.class);
        if (response.getStatusCode().equals(HttpStatus.OK)) {
            String message = (String) response.getBody().get("status");
            return message.equalsIgnoreCase("success");
        } else return false;
    }
}
