package com.duoc.backend.repository;

import com.duoc.backend.entity.Envio;
import com.duoc.backend.entity.EstadoEnvio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EnvioRepository extends JpaRepository<Envio, Long> {

    List<Envio> findByEstado(EstadoEnvio estado);

    List<Envio> findByEstadoAndFechaCreacionBetween(
            EstadoEnvio estado,
            LocalDateTime from,
            LocalDateTime to
    );

    List<Envio> findByFechaCreacionBetween(
            LocalDateTime from,
            LocalDateTime to
    );
}