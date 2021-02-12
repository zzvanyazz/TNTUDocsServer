package com.tntu.server.docs.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class Swagger2Config {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.tntu.server.docs.communication"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiEndpointsInfo())
                .securitySchemes(Collections.singletonList(securityScheme()))
                .securityContexts(Collections.singletonList(securityContext()));
    }

    private ApiInfo apiEndpointsInfo() {
        return new ApiInfoBuilder()
                .title("TNTU")
                .description("TNTU Docks Management Server API")
                .version("1.0")
                .build();
    }

    private SecurityScheme securityScheme() {
        return new ApiKey("JWT", "Authorization", "header");
    }

    private SecurityContext securityContext() {
        AuthorizationScope[] scopes = new AuthorizationScope[]{
                new AuthorizationScope("global", "Access everything"),
        };

        return SecurityContext.builder()
                .securityReferences(Collections.singletonList(new SecurityReference("JWT", scopes)))
                .operationSelector(x -> true)
                .build();
    }
}