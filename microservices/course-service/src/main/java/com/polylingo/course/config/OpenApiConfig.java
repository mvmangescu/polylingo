package com.polylingo.course.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Value("${server.port:8083}")
    private String serverPort;

    @Bean
    public OpenAPI courseServiceOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Course Service API")
                .description("Course management microservice for Polylingo language learning platform. " +
                           "Manages courses, modules, and lessons for learning Romanian, English, and Spanish.")
                .version("1.0.0")
                .contact(new Contact()
                    .name("Polylingo Team")
                    .email("support@polylingo.com")
                    .url("https://polylingo.com"))
                .license(new License()
                    .name("MIT License")
                    .url("https://opensource.org/licenses/MIT")))
            .servers(List.of(
                new Server()
                    .url("http://localhost:" + serverPort)
                    .description("Development Server"),
                new Server()
                    .url("https://api.polylingo.com/course-service")
                    .description("Production Server")
            ));
    }
}
