package com.duoc.ms_rutaexpress_catalog.repository;

import com.duoc.ms_rutaexpress_catalog.entity.ServicioEnvio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServicioRepository extends JpaRepository<ServicioEnvio, Long> {
}