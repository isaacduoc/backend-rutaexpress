package com.duoc.ms_rutaexpress_report.service;

import com.duoc.ms_rutaexpress_report.entity.ReportEvent;
import com.duoc.ms_rutaexpress_report.repository.ReportEventRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportService {

    private final ReportEventRepository reportEventRepository;

    public ReportService(ReportEventRepository reportEventRepository) {
        this.reportEventRepository = reportEventRepository;
    }

    public List<ReportEvent> listarEventos() {
        return reportEventRepository.findAllByOrderByFechaDesc();
    }

    public List<ReportEvent> obtenerEventosPorRango(
            LocalDateTime from,
            LocalDateTime to) {

        return reportEventRepository.findByFechaBetween(from, to);
    }

    public long contarPorEstado(String estado) {
        return reportEventRepository.findByEstado(estado).size();
    }

    public long totalEnvios() {
        return reportEventRepository.count();
    }

    public Map<String, Long> obtenerTopServicios(
            LocalDateTime from,
            LocalDateTime to) {

        List<ReportEvent> eventos =
                reportEventRepository.findByFechaBetween(from, to);

        return eventos.stream()
                .filter(evento -> evento.getServicio() != null
                        && !evento.getServicio().isBlank())
                .collect(Collectors.groupingBy(
                        ReportEvent::getServicio,
                        Collectors.counting()
                ));
    }
}