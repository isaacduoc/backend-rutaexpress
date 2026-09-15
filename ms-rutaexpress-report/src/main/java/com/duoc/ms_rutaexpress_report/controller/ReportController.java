package com.duoc.ms_rutaexpress_report.controller;

import com.duoc.ms_rutaexpress_report.entity.ReportEvent;
import com.duoc.ms_rutaexpress_report.service.ReportService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/report")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping
    public List<ReportEvent> listarEventos() {
        return reportService.listarEventos();
    }

    @GetMapping("/kpis")
    public Map<String, Object> obtenerKpis(
            @RequestParam(defaultValue = "last24h") String range) {

        LocalDateTime to = LocalDateTime.now();
        LocalDateTime from = to.minusHours(24);

        List<ReportEvent> eventos =
                reportService.obtenerEventosPorRango(from, to);

        Map<String, Object> kpis = new HashMap<>();

        kpis.put("range", range);
        kpis.put("totalEventos", eventos.size());
        kpis.put("creados", contarEstado(eventos, "CREADO"));
        kpis.put("aceptados", contarEstado(eventos, "ACEPTADO"));
        kpis.put("enBodega", contarEstado(eventos, "EN_BODEGA"));
        kpis.put("enRuta", contarEstado(eventos, "EN_RUTA"));
        kpis.put("entregados", contarEstado(eventos, "ENTREGADO"));
        kpis.put("cancelados", contarEstado(eventos, "CANCELADO"));

        return kpis;
    }

    @GetMapping("/top-services")
    public Map<String, Object> obtenerTopServices(
            @RequestParam(defaultValue = "last7d") String range) {

        LocalDateTime to = LocalDateTime.now();
        LocalDateTime from = to.minusDays(7);

        Map<String, Long> servicios =
                reportService.obtenerTopServicios(from, to);

        Map<String, Object> resultado = new HashMap<>();

        resultado.put("range", range);
        resultado.put("servicios", servicios);

        return resultado;
    }

    private long contarEstado(List<ReportEvent> eventos, String estado) {
        return eventos.stream()
                .filter(evento -> estado.equals(evento.getEstado()))
                .count();
    }
}