package com.gabrielspassos.cliente.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.any;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.gabrielspassos.cliente"))
                .paths(any())
                .build()
                .apiInfo(infoApi().build());
    }

    private ApiInfoBuilder infoApi() {

        ApiInfoBuilder apiInfoBuilder = new ApiInfoBuilder();

        apiInfoBuilder.title("Cliente-Service");
        apiInfoBuilder.description("Api para estudos.");
        apiInfoBuilder.version("1.0");
        apiInfoBuilder.termsOfServiceUrl("Termo de uso: Deve ser usada para estudos.");
        apiInfoBuilder.license("Licença - Open Source");
        apiInfoBuilder.contact(contact());

        return apiInfoBuilder;
    }

    private Contact contact() {
        return new Contact(
                "Gabriel Santos dos Passos",
                "https://blogcoreengineering.wordpress.com/",
                "gabrielsantos45725@gmail.com");
    }

}