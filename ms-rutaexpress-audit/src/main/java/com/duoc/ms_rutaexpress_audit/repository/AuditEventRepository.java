package com.duoc.ms_rutaexpress_audit.repository;

import com.duoc.ms_rutaexpress_audit.entity.AuditEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuditEventRepository extends JpaRepository<AuditEvent, Long> {

    List<AuditEvent> findByShipmentIdOrderByFechaAsc(Long shipmentId);

    List<AuditEvent> findByCodigoSeguimientoOrderByFechaAsc(String codigoSeguimiento);

    List<AuditEvent> findAllByOrderByFechaDesc();
}