package elice04_pikacharger.pikacharger.util;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
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
//        String jwt = "JWT";
//        SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwt);
//        Components components = new Components().addSecuritySchemes(jwt, new SecurityScheme()
//                .name(jwt)
//                .type(SecurityScheme.Type.HTTP)
//                .scheme("bearer")
//                .bearerFormat("JWT")
//        );
////        Info info = new Info()
////                .title("test swagger")
////                .version("1.0")
////                .description("<li>서비스 가동 일지 : " + serverStartTime + "</li>");
//
//        return new OpenAPI()
//                .components(new Components())
//                .info(apiInfo())
//                .addSecurityItem(securityRequirement)
//                .components(components);
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes(SECURITY_SCHEME_NAME, new SecurityScheme()
                                .type(SecurityScheme.Type.OAUTH2)
                                .description("OAuth2 로그인")
                                .flows(new OAuthFlows()
                                        .authorizationCode(new OAuthFlow()
                                                .authorizationUrl("/oauth2/authorization/google") // OAuth2 제공자의 인증 URL
                                                .tokenUrl("/login/oauth2/code/google") // 토큰 URL
                                                .refreshUrl("/oauth2/token/refresh") // 리프레시 토큰 URL (필요한 경우)
                                        )))
                        .addSecuritySchemes("jwt", new SecurityScheme()
                                .name("JWT")
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                        ))
                .info(apiInfo())
                .addSecurityItem(new SecurityRequirement().addList("oauth2"))
                .addSecurityItem(new SecurityRequirement().addList("jwt"));
    }
    private Info apiInfo(){
        return new Info()
                .title("Pikacharger API")
                .description("Pikacharger API")
                .version("1.0");
    }
}
