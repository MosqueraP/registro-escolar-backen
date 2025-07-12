package com.registroescolar.backend.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        Server server = new Server();
        server.setUrl("https://miapp-1002497481751.us-central1.run.app"); // Usa la URL real HTTPS
        server.setDescription("Cloud Run");

        return new OpenAPI()
                .info(new Info()
                        .title("Registro Escolar API")
                        .version("1.0")
                        .description("Documentación del backend desplegado en Cloud Run"))
                .servers(List.of(server));
    }
}