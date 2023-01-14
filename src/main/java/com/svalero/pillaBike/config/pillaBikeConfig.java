package com.svalero.pillaBike.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class pillaBikeConfig {
    // Clase de configuracion que va a crear objetos desde cualquier sitio - objetos accesibles desde cualquier parte del proyecto
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
