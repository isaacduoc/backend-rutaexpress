package com.duoc.ms_rutaexpress_audit.controller;

import com.duoc.ms_rutaexpress_audit.entity.AuditEvent;
import com.duoc.ms_rutaexpress_audit.repository.AuditEventRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/audit")
public class AuditController {

    private final AuditEventRepository auditEventRepository;

    public AuditController(AuditEventRepository auditEventRepository) {
        this.auditEventRepository = auditEventRepository;
    }

    @GetMapping
    public List<AuditEvent> listarEventos() {
        return auditEventRepository.findAllByOrderByFechaDesc();
    }

    @GetMapping("/shipment/{shipmentId}")
    public List<AuditEvent> obtenerTimeline(@PathVariable Long shipmentId) {
        return auditEventRepository.findByShipmentIdOrderByFechaAsc(shipmentId);
    }

    @GetMapping("/tracking/{codigoSeguimiento}")
    public List<AuditEvent> obtenerPorCodigo(
            @PathVariable String codigoSeguimiento) {

        return auditEventRepository
                .findByCodigoSeguimientoOrderByFechaAsc(codigoSeguimiento);
    }
}