package com.duoc.ms_rutaexpress_report.event;

import java.time.LocalDateTime;

public class ShipmentEvent {

    private Long id;
    private String codigoSeguimiento;
    private String estado;
    private LocalDateTime fecha;
    private String correoDestinatario;
    private String servicio;

    public ShipmentEvent() {
    }

    public ShipmentEvent(Long id, String codigoSeguimiento,
                         String estado, LocalDateTime fecha,
                         String correoDestinatario, String servicio) {
        this.id = id;
        this.codigoSeguimiento = codigoSeguimiento;
        this.estado = estado;
        this.fecha = fecha;
        this.correoDestinatario = correoDestinatario;
        this.servicio = servicio;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }
}