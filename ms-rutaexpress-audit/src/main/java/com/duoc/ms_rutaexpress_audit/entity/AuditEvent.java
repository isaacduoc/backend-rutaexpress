package com.duoc.ms_rutaexpress_audit.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "audit_events")
public class AuditEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long shipmentId;

    private String codigoSeguimiento;

    private String estado;

    private LocalDateTime fecha;

    private String correoDestinatario;

    public AuditEvent() {
    }

    public AuditEvent(Long shipmentId, String codigoSeguimiento,
                      String estado, LocalDateTime fecha,
                      String correoDestinatario) {
        this.shipmentId = shipmentId;
        this.codigoSeguimiento = codigoSeguimiento;
        this.estado = estado;
        this.fecha = fecha;
        this.correoDestinatario = correoDestinatario;
    }

    public Long getId() {
        return id;
    }

    public Long getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(Long shipmentId) {
        this.shipmentId = shipmentId;
    }

    public String getCodigoSeguimiento() {
        return codigoSeguimiento;
    }

    public void setCodigoSeguimiento(String codigoSeguimiento) {
        this.codigoSeguimiento = codigoSeguimiento;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getCorreoDestinatario() {
        return correoDestinatario;
    }

    public void setCorreoDestinatario(String correoDestinatario) {
        this.correoDestinatario = correoDestinatario;
    }
}