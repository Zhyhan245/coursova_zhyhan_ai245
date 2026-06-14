package ua.opnu.labwork2.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Backend-сервіс управління проєктами та задачами")
                        .version("1.0.0")
                        .description("""
                                Backend REST API для управління:
                                - проєктами
                                - задачами
                                - користувачами
                                - тегами
                                - коментарями

                                Система реалізує CRUD-операції, зв’язки між сутностями,
                                а також підтримує аналітичні запити та пошук.
                                
                                Технології: Spring Boot, Spring Data JPA, Hibernate, REST API
                                """)
                        .contact(new Contact()
                                .name("Жиган Дар'я Олександрівна")
                                .email("9140015@stud.op.edu.ua")
                        )
                        .license(new License()
                                .name("Educational Use Only")
                        )
                );
    }
}