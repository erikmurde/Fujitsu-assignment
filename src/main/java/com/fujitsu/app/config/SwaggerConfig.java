package com.fujitsu.app.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration class for setting up Swagger UI.
 */
@Configuration
public class SwaggerConfig implements WebMvcConfigurer {

    /**
     * Configures information to display on REST API documentation homepage.
     *
     * @return information to display on API documentation homepage
     */
    @Bean
    public OpenAPI applicationOpenAPI() {
        Info apiInfo = new Info()
                .title("Fujitsu test assignment REST API")
                .version("1.0")
                .description("REST API documentation for Fujitsu test assignment.");

        return new OpenAPI().info(apiInfo);
    }
}
