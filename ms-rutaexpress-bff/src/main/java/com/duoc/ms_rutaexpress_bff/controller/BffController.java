package com.duoc.ms_rutaexpress_bff.controller;

import com.duoc.ms_rutaexpress_bff.dto.CambiarEstadoBffRequest;
import com.duoc.ms_rutaexpress_bff.dto.CrearEnvioBffRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/bff/v1/shipments")
public class BffController {

    private final RestTemplate restTemplate;

    @Value("${shipments.service.url}")
    private String shipmentsUrl;

    public BffController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostMapping
    public ResponseEntity<Object> crearEnvio(@RequestBody CrearEnvioBffRequest request,
                                            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader) {
        HttpHeaders headers = crearHeadersConToken(authHeader);
        HttpEntity<CrearEnvioBffRequest> entity = new HttpEntity<>(request, headers);
        return restTemplate.postForEntity(shipmentsUrl, entity, Object.class);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> obtenerEnvio(@PathVariable Long id,
                                               @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader) {
        String url = shipmentsUrl + "/" + id;
        HttpHeaders headers = crearHeadersConToken(authHeader);
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<Object> cambiarEstado(@PathVariable Long id, 
                                               @RequestBody CambiarEstadoBffRequest request,
                                               @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader) {
        String url = shipmentsUrl + "/" + id + "/estado";
        HttpHeaders headers = crearHeadersConToken(authHeader);
        HttpEntity<CambiarEstadoBffRequest> entity = new HttpEntity<>(request, headers);
        return restTemplate.exchange(url, HttpMethod.PUT, entity, Object.class);
    }

    private HttpHeaders crearHeadersConToken(String authHeader) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        if (authHeader != null) {
            headers.set(HttpHeaders.AUTHORIZATION, authHeader);
        }
        return headers;
    }
}