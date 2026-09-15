package com.duoc.backend.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_DIRECT = "x.direct.envios";
    
    // Nombres de Colas Principales
    public static final String QUEUE_EMAIL = "q.cmd.email";
    public static final String QUEUE_WAREHOUSE = "q.cmd.warehouse";
    public static final String QUEUE_LABEL = "q.cmd.label";

    // Nombres de DLQs
    public static final String QUEUE_EMAIL_DLQ = "q.cmd.email.dlq";
    public static final String QUEUE_WAREHOUSE_DLQ = "q.cmd.warehouse.dlq";
    public static final String QUEUE_LABEL_DLQ = "q.cmd.label.dlq";

    // Routing Keys
    public static final String ROUTING_KEY_EMAIL = "cmd.email";
    public static final String ROUTING_KEY_WAREHOUSE = "cmd.warehouse";
    public static final String ROUTING_KEY_LABEL = "cmd.label";

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE_DIRECT);
    }

    // --- CONFIGURACIÓN DE COLAS CON DLQ ---

    @Bean
    public Queue emailQueue() {
        return QueueBuilder.durable(QUEUE_EMAIL)
                .withArgument("x-dead-letter-exchange", "")
                .withArgument("x-dead-letter-routing-key", QUEUE_EMAIL_DLQ)
                .build();
    }

    @Bean
    public Queue warehouseQueue() {
        return QueueBuilder.durable(QUEUE_WAREHOUSE)
                .withArgument("x-dead-letter-exchange", "")
                .withArgument("x-dead-letter-routing-key", QUEUE_WAREHOUSE_DLQ)
                .build();
    }

    @Bean
    public Queue labelQueue() {
        return QueueBuilder.durable(QUEUE_LABEL)
                .withArgument("x-dead-letter-exchange", "")
                .withArgument("x-dead-letter-routing-key", QUEUE_LABEL_DLQ)
                .build();
    }

    // --- DECLARACIÓN DE DLQs ---

    @Bean
    public Queue emailDlq() {
        return QueueBuilder.durable(QUEUE_EMAIL_DLQ).build();
    }

    @Bean
    public Queue warehouseDlq() {
        return QueueBuilder.durable(QUEUE_WAREHOUSE_DLQ).build();
    }

    @Bean
    public Queue labelDlq() {
        return QueueBuilder.durable(QUEUE_LABEL_DLQ).build();
    }

    // --- BINDINGS ---

    @Bean
    public Binding emailBinding(Queue emailQueue, DirectExchange exchange) {
        return BindingBuilder.bind(emailQueue).to(exchange).with(ROUTING_KEY_EMAIL);
    }

    @Bean
    public Binding warehouseBinding(Queue warehouseQueue, DirectExchange exchange) {
        return BindingBuilder.bind(warehouseQueue).to(exchange).with(ROUTING_KEY_WAREHOUSE);
    }

    @Bean
    public Binding labelBinding(Queue labelQueue, DirectExchange exchange) {
        return BindingBuilder.bind(labelQueue).to(exchange).with(ROUTING_KEY_LABEL);
    }

    // Convertidor de mensajes a JSON
    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}