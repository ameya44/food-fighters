package com.bitlu.foodfightersapi.foodfighters.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;

@Service
public class ExternalFoodService {

    private final RestTemplate restTemplate;

    @Value("${usda.api.key}")
    private String apiKey;

    public ExternalFoodService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public String searchFoods(String query) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<>(headers);

        String url = UriComponentsBuilder
                .fromHttpUrl("https://api.nal.usda.gov/fdc/v1/foods/search")
                .queryParam("api_key", apiKey)
                .queryParam("query", query)
                .build()
                .toUriString();
        try {
            System.out.println("Final URL: " + url);
            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    String.class
            );
            return response.getBody();
        } catch (HttpClientErrorException e) {
            System.out.println("HTTP Error: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
            throw e;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            throw e;
        }
    }
}
//    public List<Map<String, Object>> searchFoods(String query) {
//        String url = "https://api.nal.usda.gov/fdc/v1/foods/search?query=" +
//                UriUtils.encode(query, StandardCharsets.UTF_8) +
//                "&api_key=" + apiKey;
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Accept", "application/json");
//        headers.set("User-Agent", "Mozilla/5.0");
//
//        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
//
//        ResponseEntity<Map> response = restTemplate.exchange(
//                url,
//                HttpMethod.GET,
//                requestEntity,
//                Map.class
//        );
//
//        if (response.getStatusCode() == HttpStatus.OK) {
//            return (List<Map<String, Object>>) response.getBody().getOrDefault("foods", List.of());
//        } else {
//            throw new RuntimeException("USDA API error: " + response.getStatusCode());
//        }
//    }


