package com.github.guiziin227.restspringboot.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    //objeto instanciado e gerenciado pelo Spring
    @Bean
    OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Rest API with Spring Boot, kubernetes and docker")
                        .version("1.0.0")
                        .description("Study project for learning purposes")
//                        .license(new License()
//                                .name("Apache 2.0")
//                                .url(
//                                "https://www.apache.org/licenses/LICENSE-2.0"
//                        ))
                );

    }
}
