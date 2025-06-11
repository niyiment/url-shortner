package com.niyiment.urlshortener.config;


import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .info(new Info()
                        .title("URL Shortener API")
                        .description("API for URL Shortener")
                        .version("v0.0.1")
                        .contact(new Contact()
                                .name("Niyiment")
                                .url("https://github.com/niyiment")
                        )
                );
    }

}
