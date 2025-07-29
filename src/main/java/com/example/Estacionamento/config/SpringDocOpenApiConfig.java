package com.example.Estacionamento.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocOpenApiConfig {

    @Bean
    public OpenAPI  openAPI(){
        return new OpenAPI()
                .components(new Components().addSecuritySchemes("security",securityScheme()))
                .info(
                new Info().title("Rest API - Spring Park")
                        .description("API para a gestão de estacionamento de veiculos")
                        .version("v1")
                        .license(new License().name("APACHE 2.0").url("https://www.apache.org/licenses/LICENSE-2.0.html"))
                        .contact(new Contact().name("caua").email("caua.oliversouza@gmail.com"))
        );
    }
    private SecurityScheme securityScheme(){
        return new SecurityScheme()
                .description("Insita um Bearer Token valido para Prosseguir")
                .type(SecurityScheme.Type.HTTP)
                .in(SecurityScheme.In.HEADER)
                .scheme("bearer")
                .bearerFormat("JWT")
                .name("security");

    }
}
