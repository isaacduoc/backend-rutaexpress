package com.duoc.ms_rutaexpress_bff.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/bff/v1/catalog")
public class CatalogBffController {

    private final RestTemplate restTemplate;

    @Value("${catalog.service.url}")
    private String catalogUrl;

    public CatalogBffController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/services")
    public ResponseEntity<Object> listarServicios(@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader) {
        HttpHeaders headers = crearHeaders(authHeader);
        return restTemplate.exchange(catalogUrl + "/services", HttpMethod.GET, new HttpEntity<>(headers), Object.class);
    }

    @PostMapping("/services")
    public ResponseEntity<Object> crearServicio(@RequestBody Object body, 
                                                @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader) {
        HttpHeaders headers = crearHeaders(authHeader);
        return restTemplate.postForEntity(catalogUrl + "/services", new HttpEntity<>(body, headers), Object.class);
    }

    private HttpHeaders crearHeaders(String authHeader) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        if (authHeader != null) headers.set(HttpHeaders.AUTHORIZATION, authHeader);
        return headers;
    }
}