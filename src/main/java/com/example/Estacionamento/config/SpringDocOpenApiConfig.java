package com.example.Estacionamento.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocOpenApiConfig {

    @Bean
    public OpenAPI  openAPI(){
        return new OpenAPI().info(
                new Info().title("Rest API - Spring Park")
                        .description("API para a gestão de estacionamento de veiculos")
                        .version("v1")
                        .license(new License().name("APACHE 2.0").url("https://www.apache.org/licenses/LICENSE-2.0.html"))
                        .contact(new Contact().name("caua").email("caua.oliversouza@gmail.com"))
        );
    }
}
