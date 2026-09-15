package com.duoc.backend.event;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {

    private final KafkaTemplate<String, ShipmentEvent> kafkaTemplate;

    @Value("${app.kafka.topic.shipments-events}")
    private String topic;

    public KafkaProducer(KafkaTemplate<String, ShipmentEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void enviarEvento(ShipmentEvent evento) {
        kafkaTemplate.send(topic, evento.getId().toString(), evento);
    }
}