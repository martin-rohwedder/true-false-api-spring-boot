package dk.martinrohwedder.tfgapi.configurations;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

/**
 * Configuration for the Openapi Swagger UI
 */
@Configuration
public class SwaggerConfig {
    @Value("${spring.application.name}")
    private String appName;

    @Value("${spring.application.description}")
    private String appDescription;

    @Value("${spring.application.version}")
    private String appVersion;

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public")
                .pathsToMatch("/**")
                .build();
    }

    @Bean
    public OpenAPI springOpenApiConfig() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("api_key", new SecurityScheme()
                                .type(SecurityScheme.Type.APIKEY)
                                .description("Api Key access")
                                .in(SecurityScheme.In.HEADER)
                                .name("X-API-Key")))
                .security(Collections.singletonList(
                        new SecurityRequirement().addList("api_key")))
                .info(new Info().title(appName)
                        .description(appDescription)
                        .version(String.format("V%s", appVersion)));
    }
}
