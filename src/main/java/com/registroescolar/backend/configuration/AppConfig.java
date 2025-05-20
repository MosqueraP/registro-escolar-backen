package com.registroescolar.backend.configuration;

import com.registroescolar.backend.dto.*;
import com.registroescolar.backend.model.*;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class AppConfig {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}