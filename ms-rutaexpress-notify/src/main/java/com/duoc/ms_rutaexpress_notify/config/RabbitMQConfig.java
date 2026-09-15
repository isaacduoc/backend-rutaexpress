package com.duoc.ms_rutaexpress_notify.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_DIRECT = "x.direct.envios";
    
    public static final String QUEUE_EMAIL = "q.cmd.email";
    public static final String QUEUE_WAREHOUSE = "q.cmd.warehouse";
    public static final String QUEUE_LABEL = "q.cmd.label";

    public static final String ROUTING_KEY_EMAIL = "cmd.email";
    public static final String ROUTING_KEY_WAREHOUSE = "cmd.warehouse";
    public static final String ROUTING_KEY_LABEL = "cmd.label";

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE_DIRECT);
    }

    @Bean
    public Queue emailQueue() {
        return QueueBuilder.durable(QUEUE_EMAIL).build();
    }

    @Bean
    public Queue warehouseQueue() {
        return QueueBuilder.durable(QUEUE_WAREHOUSE).build();
    }

    @Bean
    public Queue labelQueue() {
        return QueueBuilder.durable(QUEUE_LABEL).build();
    }

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
}