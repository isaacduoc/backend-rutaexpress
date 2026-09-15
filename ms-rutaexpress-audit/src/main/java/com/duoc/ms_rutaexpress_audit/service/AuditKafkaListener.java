package com.duoc.ms_rutaexpress_audit.service;

import com.duoc.ms_rutaexpress_audit.entity.AuditEvent;
import com.duoc.ms_rutaexpress_audit.event.ShipmentEvent;
import com.duoc.ms_rutaexpress_audit.repository.AuditEventRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class AuditKafkaListener {

    private final AuditEventRepository auditEventRepository;

    public AuditKafkaListener(AuditEventRepository auditEventRepository) {
        this.auditEventRepository = auditEventRepository;
    }

    @KafkaListener(
            topics = "${app.kafka.topic.shipments-events}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void recibirEvento(ShipmentEvent evento) {

        AuditEvent auditEvent = new AuditEvent(
                evento.getId(),
                evento.getCodigoSeguimiento(),
                evento.getEstado(),
                evento.getFecha(),
                evento.getCorreoDestinatario()
        );

        auditEventRepository.save(auditEvent);

        System.out.println(
                "Evento recibido por Audit: "
                        + evento.getCodigoSeguimiento()
                        + " - Estado: "
                        + evento.getEstado()
        );
    }
}