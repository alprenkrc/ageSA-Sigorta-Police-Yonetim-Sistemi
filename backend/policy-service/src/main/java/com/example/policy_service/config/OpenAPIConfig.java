package com.example.policy_service.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI policyServiceOpenAPI() {
        Server server = new Server();
        server.setUrl("http://localhost:8082");
        server.setDescription("Policy Service Local Development Server");

        Contact contact = new Contact();
        contact.setEmail("admin@insurance.com");
        contact.setName("Insurance Team");

        Info info = new Info()
                .title("Policy Service API")
                .version("1.0")
                .contact(contact)
                .description("Sigorta Poliçe Yönetim Sistemi - Poliçe Servisi API Dokümantasyonu")
                .termsOfService("https://www.insurance.com/terms")
                .license(new io.swagger.v3.oas.models.info.License().name("MIT License").url("https://choosealicense.com/licenses/mit/"));

        return new OpenAPI().info(info).servers(List.of(server));
    }
}
