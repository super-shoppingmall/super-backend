package com.github.superbackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
<<<<<<< HEAD
import org.springframework.context.annotation.Lazy;
=======
>>>>>>> 77c5796dd2bbf019c9786d9f7aef41ff62bcc57f
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
<<<<<<< HEAD
@Lazy
=======
>>>>>>> 77c5796dd2bbf019c9786d9f7aef41ff62bcc57f
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.github.superbackend.controller"))
                .paths(PathSelectors.any())
                .build();
    }
<<<<<<< HEAD
}
=======
}
>>>>>>> 77c5796dd2bbf019c9786d9f7aef41ff62bcc57f
