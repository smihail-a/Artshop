package config;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;


@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Artshop API",
                version = "1.0",
                description = "REST API for artists to upload, sell, and review artwork"
        )
)
public class OpenApiConfig {
}