package com.duoc.backend.service;

import com.duoc.backend.config.RabbitMQConfig;
import com.duoc.backend.entity.Envio;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class NotificationPublisher {

    private final RabbitTemplate rabbitTemplate;

    public NotificationPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void enviarNotificacionCambioEstado(Envio envio) {
        // Armamos un mensaje en texto plano / JSON para que el consumidor lo reciba sin problemas
        String mensaje = String.format("{\"id\": %d, \"estado\": \"%s\", \"destinatario\": \"%s\"}", 
            envio.getId(), 
            envio.getEstado(), 
            envio.getNombreDestinatario());

        // 1. Notificación por Email
        rabbitTemplate.convertAndSend(
            RabbitMQConfig.EXCHANGE_DIRECT,
            RabbitMQConfig.ROUTING_KEY_EMAIL,
            mensaje
        );

        // 2. Notificación a Bodega/Warehouse
        rabbitTemplate.convertAndSend(
            RabbitMQConfig.EXCHANGE_DIRECT,
            RabbitMQConfig.ROUTING_KEY_WAREHOUSE,
            mensaje
        );

        // 3. Notificación para Emisión de Etiqueta
        rabbitTemplate.convertAndSend(
            RabbitMQConfig.EXCHANGE_DIRECT,
            RabbitMQConfig.ROUTING_KEY_LABEL,
            mensaje
        );

        System.out.println(">>> [BACKEND] Notificación enviada exitosamente a RabbitMQ: " + mensaje);
    }
}