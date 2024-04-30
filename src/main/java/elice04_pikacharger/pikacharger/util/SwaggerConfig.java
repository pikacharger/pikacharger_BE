package elice04_pikacharger.pikacharger.util;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.util.Date;

@Configuration
public class SwaggerConfig {

    private static final String SECURITY_SCHEME_NAME = "authorization";

    private String serverStartTime = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());

    @Bean
    public OpenAPI openAPI(){
        String jwt = "JWT";
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwt);
        Components components = new Components().addSecuritySchemes(jwt, new SecurityScheme()
                .name(jwt)
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
        );
//        Info info = new Info()
//                .title("test swagger")
//                .version("1.0")
//                .description("<li>서비스 가동 일지 : " + serverStartTime + "</li>");

        return new OpenAPI()
                .components(new Components())
                .info(apiInfo())
                .addSecurityItem(securityRequirement)
                .components(components);
    }
    private Info apiInfo(){
        return new Info()
                .title("Pikacharger API")
                .description("Pikacharger API")
                .version("1.0");
    }
}
