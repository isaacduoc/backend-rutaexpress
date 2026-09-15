package com.duoc.ms_rutaexpress_report.service;

import com.duoc.ms_rutaexpress_report.entity.ReportEvent;
import com.duoc.ms_rutaexpress_report.event.ShipmentEvent;
import com.duoc.ms_rutaexpress_report.repository.ReportEventRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ReportKafkaListener {

    private final ReportEventRepository reportEventRepository;

    public ReportKafkaListener(ReportEventRepository reportEventRepository) {
        this.reportEventRepository = reportEventRepository;
    }

    @KafkaListener(
            topics = "${app.kafka.topic.shipments-events}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void recibirEvento(ShipmentEvent evento) {

        ReportEvent reportEvent = new ReportEvent(
                evento.getId(),
                evento.getCodigoSeguimiento(),
                evento.getEstado(),
                evento.getFecha(),
                evento.getCorreoDestinatario(),
                evento.getServicio()
        );

        reportEventRepository.save(reportEvent);

        System.out.println(
                "Evento recibido por Report: "
                        + evento.getCodigoSeguimiento()
                        + " - Estado: "
                        + evento.getEstado()
                        + " - Servicio: "
                        + evento.getServicio()
        );
    }
}