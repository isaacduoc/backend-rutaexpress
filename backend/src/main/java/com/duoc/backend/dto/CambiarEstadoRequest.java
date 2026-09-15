package com.duoc.backend.dto;

import com.duoc.backend.entity.EstadoEnvio;

public class CambiarEstadoRequest {
    private EstadoEnvio estado;

    public EstadoEnvio getEstado() { return estado; }
    public void setEstado(EstadoEnvio estado) { this.estado = estado; }
}