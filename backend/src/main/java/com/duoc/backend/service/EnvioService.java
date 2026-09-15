package com.duoc.backend.service;

import com.duoc.backend.dto.CrearEnvioRequest;
import com.duoc.backend.entity.Envio;
import com.duoc.backend.entity.EstadoEnvio;
import com.duoc.backend.repository.EnvioRepository;
import com.duoc.backend.event.KafkaProducer;
import com.duoc.backend.event.ShipmentEvent;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class EnvioService {

    private final EnvioRepository envioRepository;
    private final NotificationPublisher notificationPublisher;
    private final RestTemplate restTemplate;
    private final KafkaProducer kafkaProducer;

    public EnvioService(
            EnvioRepository envioRepository,
            NotificationPublisher notificationPublisher,
            RestTemplate restTemplate,
            KafkaProducer kafkaProducer) {

        this.envioRepository = envioRepository;
        this.notificationPublisher = notificationPublisher;
        this.restTemplate = restTemplate;
        this.kafkaProducer = kafkaProducer;
    }

    public Envio crearEnvio(CrearEnvioRequest datos) {

        Envio envio = new Envio();

        envio.setCodigoSeguimiento(
                "REX-" +
                UUID.randomUUID()
                        .toString()
                        .substring(0, 8)
                        .toUpperCase()
        );

        envio.setCorreoRemitente(
                datos.getCorreoRemitente()
        );

        envio.setNombreDestinatario(
                datos.getNombreDestinatario()
        );

        envio.setCorreoDestinatario(
                datos.getCorreoDestinatario()
        );

        envio.setDireccionOrigen(
                datos.getDireccionOrigen()
        );

        envio.setDireccionDestino(
                datos.getDireccionDestino()
        );

        envio.setServicio(
                datos.getServicio()
        );

        envio.setEstado(
                EstadoEnvio.CREADO
        );

        Envio envioGuardado =
                envioRepository.save(envio);

        /*
         * TEMPORALMENTE DESACTIVADO.
         *
         * Queremos comprobar si Kafka está
         * causando la demora al crear envíos.
         */

        // ShipmentEvent evento =
        //         new ShipmentEvent(
        //                 envioGuardado.getId(),
        //                 envioGuardado.getCodigoSeguimiento(),
        //                 envioGuardado.getEstado(),
        //                 LocalDateTime.now(),
        //                 envioGuardado.getCorreoDestinatario(),
        //                 envioGuardado.getServicio()
        //         );

        // kafkaProducer.enviarEvento(evento);

        return envioGuardado;
    }

    public Envio obtenerPorId(Long id) {

        return envioRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Envío no encontrado con ID: " + id
                        )
                );
    }

    public List<Envio> listarEnvios(
            EstadoEnvio estado) {

        if (estado != null) {
            return envioRepository
                    .findByEstado(estado);
        }

        return envioRepository.findAll();
    }

    public Envio cambiarEstado(
            Long id,
            EstadoEnvio nuevoEstado) {

        Envio envio =
                obtenerPorId(id);

        /*
         * Regla de negocio:
         * no permitir CREADO -> EN_RUTA directamente.
         */
        if (
                nuevoEstado == EstadoEnvio.EN_RUTA
                &&
                envio.getEstado() == EstadoEnvio.CREADO
        ) {

            throw new IllegalArgumentException(
                    "No se puede cambiar el estado a EN_RUTA " +
                    "sin haber sido ACEPTADO previamente."
            );
        }

        /*
         * Descontar capacidad del catálogo
         * cuando el envío pasa a ACEPTADO.
         */
        if (
                nuevoEstado == EstadoEnvio.ACEPTADO
        ) {

            try {

                String catalogUrl =
                        "http://localhost:8083" +
                        "/api/catalog/services/1/descontar";

                restTemplate.put(
                        catalogUrl,
                        null
                );

            } catch (Exception e) {

                throw new RuntimeException(
                        "No se pudo actualizar la capacidad " +
                        "en el servicio de catálogo: " +
                        e.getMessage()
                );
            }
        }

        envio.setEstado(
                nuevoEstado
        );

        Envio envioActualizado =
                envioRepository.save(envio);

        /*
         * RabbitMQ temporalmente desactivado.
         */

        // notificationPublisher
        //         .enviarNotificacionCambioEstado(
        //                 envioActualizado
        //         );

        /*
         * Kafka ACTIVO para cambios de estado.
         * Así podemos seguir probando Reportes.
         */

        ShipmentEvent evento =
                new ShipmentEvent(
                        envioActualizado.getId(),
                        envioActualizado.getCodigoSeguimiento(),
                        envioActualizado.getEstado(),
                        LocalDateTime.now(),
                        envioActualizado.getCorreoDestinatario(),
                        envioActualizado.getServicio()
                );

        kafkaProducer.enviarEvento(evento);

        return envioActualizado;
    }

    public List<Envio> listarEnvios(
            EstadoEnvio estado,
            LocalDateTime from,
            LocalDateTime to) {

        if (
                estado != null
                &&
                from != null
                &&
                to != null
        ) {

            return envioRepository
                    .findByEstadoAndFechaCreacionBetween(
                            estado,
                            from,
                            to
                    );
        }

        if (estado != null) {

            return envioRepository
                    .findByEstado(estado);
        }

        if (
                from != null
                &&
                to != null
        ) {

            return envioRepository
                    .findByFechaCreacionBetween(
                            from,
                            to
                    );
        }

        return envioRepository.findAll();
    }
}