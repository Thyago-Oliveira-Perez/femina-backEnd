package br.com.femina.configurations.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public GroupedOpenApi publicApi() {
        String paths[] = {
                "/api/categorias/**",
                "/api/favortios/**",
                "/api/fornecedor/**",
                "/api/marcas/**",
                "/api/modelos/**",
                "/api/produtos/**",
        };

        return GroupedOpenApi.builder()
                .group("FeminaApi")
                .pathsToMatch(paths)
                .build();
    }

    @Bean
    public OpenAPI forumAluraOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Femina API")
                        .description("Api do Femina")
                        .version("v0.0.1")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                .components(new Components().addSecuritySchemes("bearer-key", new SecurityScheme().type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")))
                .externalDocs(new ExternalDocumentation()
                        .description("SpringShop Wiki Documentation")
                        .url("https://springshop.wiki.github.org/docs"))
                .externalDocs(new ExternalDocumentation()
                            .description("SpringShop Wiki Documentation")
                            .url("https://springshop.wiki.github.org/docs"))
                            .components(new Components()
                                    .addSecuritySchemes("bearer-key", new SecurityScheme()
                                            .type(SecurityScheme.Type.HTTP).scheme("bearer")
                                            .in(SecurityScheme.In.HEADER).bearerFormat("JWT")));
    }

}
