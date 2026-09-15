package com.duoc.ms_rutaexpress_report.repository;

import com.duoc.ms_rutaexpress_report.entity.ReportEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReportEventRepository extends JpaRepository<ReportEvent, Long> {

    List<ReportEvent> findAllByOrderByFechaDesc();

    List<ReportEvent> findByEstado(String estado);

    List<ReportEvent> findByFechaBetween(
            LocalDateTime from,
            LocalDateTime to
    );

    List<ReportEvent> findByServicio(String servicio);

    List<ReportEvent> findByFechaBetweenAndServicio(
            LocalDateTime from,
            LocalDateTime to,
            String servicio
    );
}