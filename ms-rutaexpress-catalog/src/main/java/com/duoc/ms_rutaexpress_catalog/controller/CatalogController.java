package com.duoc.ms_rutaexpress_catalog.controller;

import com.duoc.ms_rutaexpress_catalog.entity.ServicioEnvio;
import com.duoc.ms_rutaexpress_catalog.service.CatalogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/catalog")
public class CatalogController {

    private final CatalogService catalogService;

    public CatalogController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @GetMapping("/services")
    public ResponseEntity<List<ServicioEnvio>> listarServicios() {
        return ResponseEntity.ok(catalogService.listarTodos());
    }

    @PostMapping("/services")
    public ResponseEntity<ServicioEnvio> crearServicio(@RequestBody ServicioEnvio servicio) {
        return ResponseEntity.ok(catalogService.guardar(servicio));
    }

    @PutMapping("/services/{id}/descontar")
    public ResponseEntity<ServicioEnvio> descontarCapacidad(@PathVariable Long id) {
        return ResponseEntity.ok(catalogService.descontarCapacidad(id));
    }
}