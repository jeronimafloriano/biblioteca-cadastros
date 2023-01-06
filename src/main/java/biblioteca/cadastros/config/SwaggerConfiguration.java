package biblioteca.cadastros.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(servers = { @Server(url = "http://localhost:8080/", description = "Biblioteca Dona Gertrudes")})
public class SwaggerConfiguration {

    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
                .info(new Info().title("API Biblioteca")
                        .description("Spring boot aplication para cadastro de clientes e funcionários de uma biblioteca")
                        .version("v0.0.1")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org"))
                        .contact(new Contact().name("Jeronima").url("https://www.linkedin.com/in/jeronimafloriano/")))
                .externalDocs(new ExternalDocumentation()
                        .description("SpringShop Wiki Documentation")
                        .url("https://springshop.wiki.github.org/docs"));

    }


}
