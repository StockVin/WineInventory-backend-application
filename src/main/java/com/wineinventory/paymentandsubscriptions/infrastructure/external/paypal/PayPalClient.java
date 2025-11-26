package com.wineinventory.paymentandsubscriptions.infrastructure.external.paypal;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.Map;

@Component
public class PayPalClient {

    @Value("${paypal.client.id}")
    private String clientId;

    @Value("${paypal.client.secret}")
    private String clientSecret;

    @Value("${paypal.api.base-url}")
    private String baseUrl;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();


    public String generateAccessToken() {

        try {
            String auth = clientId + ":" + clientSecret;
            String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Basic " + encodedAuth);
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            HttpEntity<String> request = new HttpEntity<>("grant_type=client_credentials", headers);

            ResponseEntity<Map> response = restTemplate.exchange(
                    baseUrl + "/v1/oauth2/token",
                    HttpMethod.POST,
                    request,
                    Map.class
            );

            return (String) response.getBody().get("access_token");

        } catch (Exception e) {
            throw new RuntimeException("Error generating PayPal access token: " + e.getMessage(), e);
        }
    }

    public <T> ResponseEntity<T> post(String url, Object body, Class<T> responseType) {

        String accessToken = generateAccessToken();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Object> requestEntity = new HttpEntity<>(body, headers);

        try {
            return restTemplate.exchange(
                    baseUrl + url,
                    HttpMethod.POST,
                    requestEntity,
                    responseType
            );

        } catch (HttpClientErrorException e) {

            System.out.println("PAYPAL API ERROR");
            System.out.println("➡ URL: " + baseUrl + url);
            System.out.println("➡ REQUEST BODY:");
            try { System.out.println(objectMapper.writeValueAsString(body)); }
            catch (Exception ignored) {}

            System.out.println("➡ PAYPAL RESPONSE:");
            System.out.println(e.getResponseBodyAsString());

            throw new RuntimeException(
                    "PayPal API Error (" + e.getStatusCode() + "): " + e.getResponseBodyAsString()
            );
        }
    }

    public <T> ResponseEntity<T> postWithHeaders(String url, Object body, Class<T> responseType, HttpHeaders customHeaders) {

        String accessToken = generateAccessToken();
        customHeaders.set("Authorization", "Bearer " + accessToken);
        customHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Object> requestEntity = new HttpEntity<>(body, customHeaders);

        try {
            return restTemplate.exchange(
                    baseUrl + url,
                    HttpMethod.POST,
                    requestEntity,
                    responseType
            );

        } catch (HttpClientErrorException e) {

            System.out.println(" PAYPAL API ERROR");
            System.out.println("➡ URL: " + baseUrl + url);
            System.out.println("➡ REQUEST BODY:");
            try { System.out.println(objectMapper.writeValueAsString(body)); }
            catch (Exception ignored) {}

            System.out.println("➡ PAYPAL RESPONSE:");
            System.out.println(e.getResponseBodyAsString());

            throw new RuntimeException(
                    "PayPal API Error (" + e.getStatusCode() + "): " + e.getResponseBodyAsString()
            );
        }
    }
}
