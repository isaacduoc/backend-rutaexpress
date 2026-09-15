package com.duoc.ms_rutaexpress_bff.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/bff/v1/report")
public class ReportBffController {

    private final RestTemplate restTemplate;

    @Value("${report.service.url}")
    private String reportServiceUrl;

    public ReportBffController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/kpis")
    public ResponseEntity<String> getKpis(
            @RequestHeader("Authorization") String authorization) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authorization);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                reportServiceUrl + "/kpis",
                HttpMethod.GET,
                entity,
                String.class
        );

        return ResponseEntity
                .status(response.getStatusCode())
                .body(response.getBody());
    }

    @GetMapping("/top-services")
    public ResponseEntity<String> getTopServices(
            @RequestHeader("Authorization") String authorization) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authorization);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                reportServiceUrl + "/top-services",
                HttpMethod.GET,
                entity,
                String.class
        );

        return ResponseEntity
                .status(response.getStatusCode())
                .body(response.getBody());
    }
}