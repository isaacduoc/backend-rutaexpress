package com.duoc.ms_rutaexpress_catalog.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "servicios_cat")
public class ServicioEnvio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre; // Ej: "Express", "Especial", "Estándar"
    private Double tarifaBase;
    private Integer capacidadDisponible;

    public ServicioEnvio() {}

    public ServicioEnvio(String nombre, Double tarifaBase, Integer capacidadDisponible) {
        this.nombre = nombre;
        this.tarifaBase = tarifaBase;
        this.capacidadDisponible = capacidadDisponible;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Double getTarifaBase() { return tarifaBase; }
    public void setTarifaBase(Double tarifaBase) { this.tarifaBase = tarifaBase; }

    public Integer getCapacidadDisponible() { return capacidadDisponible; }
    public void setCapacidadDisponible(Integer capacidadDisponible) { this.capacidadDisponible = capacidadDisponible; }
}