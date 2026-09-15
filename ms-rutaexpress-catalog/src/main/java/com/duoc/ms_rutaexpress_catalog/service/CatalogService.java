package com.duoc.ms_rutaexpress_catalog.service;

import com.duoc.ms_rutaexpress_catalog.entity.ServicioEnvio;
import com.duoc.ms_rutaexpress_catalog.repository.ServicioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatalogService {

    private final ServicioRepository repository;

    public CatalogService(ServicioRepository repository) {
        this.repository = repository;
    }

    public List<ServicioEnvio> listarTodos() {
        return repository.findAll();
    }

    public ServicioEnvio guardar(ServicioEnvio servicio) {
        return repository.save(servicio);
    }

    public ServicioEnvio obtenerPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado: " + id));
    }

    // Regla funcional: reducir la capacidad de la flota al aceptar un envío
    public ServicioEnvio descontarCapacidad(Long id) {
        ServicioEnvio servicio = obtenerPorId(id);
        if (servicio.getCapacidadDisponible() <= 0) {
            throw new RuntimeException("Capacidad agotada para el servicio ID: " + id);
        }
        servicio.setCapacidadDisponible(servicio.getCapacidadDisponible() - 1);
        return repository.save(servicio);
    }
}