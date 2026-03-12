package com.example.razorpay.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
@RequiredArgsConstructor

public class ShiprocketClient {

    private final RestTemplate restTemplate;

    @Value("${shiprocket.base-url}")
    private String baseUrl;

    public ResponseEntity<Map> login(Map<String, String > body){
        String url = baseUrl + "/auth/login";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);
        return restTemplate.postForEntity(url, request, Map.class);
    }

    public ResponseEntity<Map> createShipment(String token, Map<String, Object> body) {
        String url = baseUrl + "/orders/create/adhoc";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        return restTemplate.postForEntity(url, request, Map.class);
    }

}
