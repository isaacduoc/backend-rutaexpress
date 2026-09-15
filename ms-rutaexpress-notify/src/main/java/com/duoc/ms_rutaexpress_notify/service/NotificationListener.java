package com.duoc.ms_rutaexpress_notify.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationListener {

    @RabbitListener(queues = "q.cmd.email")
    public void handleEmailNotification(String message) {
        System.out.println(">>> [MS-NOTIFY] [Cola Email] Procesando notificación: " + message);
    }

    @RabbitListener(queues = "q.cmd.warehouse")
    public void handleWarehouseNotification(String message) {
        System.out.println(">>> [MS-NOTIFY] [Cola Bodega] Generando ticket de picking: " + message);
    }

    @RabbitListener(queues = "q.cmd.label")
    public void handleLabelNotification(String message) {
        System.out.println(">>> [MS-NOTIFY] [Cola Etiqueta] Emitiendo guía PDF: " + message);
    }
}