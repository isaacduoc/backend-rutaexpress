package com.duoc.ms_rutaexpress_catalog.config;

import com.duoc.ms_rutaexpress_catalog.entity.ServicioEnvio;
import com.duoc.ms_rutaexpress_catalog.repository.ServicioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initDatabase(ServicioRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                repository.save(new ServicioEnvio("Express Ultima Milla", 4500.0, 50));
                repository.save(new ServicioEnvio("Estandar Regiones", 2990.0, 100));
            }
        };
    }
}