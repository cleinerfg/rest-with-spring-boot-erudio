package br.com.erudio.restwithspringbooterudio.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("REST with Spring Boot API")
                        .description("REST Api with Java, Spring Boot, Kubernetes and Docker")
                        .version("1.0")
                        .termsOfService("Some Link")
                        .license(new License()
                                .name("Apache 2.0"))
                );
    }
}
