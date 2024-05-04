package elice04_pikacharger.pikacharger.security.oauth2.config;


import elice04_pikacharger.pikacharger.security.HttpCookieOAuth2AuthorizationRequestRepository;
import elice04_pikacharger.pikacharger.security.JwtFilter;
import elice04_pikacharger.pikacharger.security.JwtUtil;
import elice04_pikacharger.pikacharger.security.oauth2.handler.OAuth2AuthenticationFailureHandler;
import elice04_pikacharger.pikacharger.security.oauth2.handler.OAuth2AuthenticationSuccessHandler;
import elice04_pikacharger.pikacharger.security.oauth2.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.savedrequest.RequestCacheAwareFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;


@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableMethodSecurity
@Configuration
public class SecurityConfig implements WebMvcConfigurer {
    private final JwtUtil jwtUtil;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;
    private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtFilter jwtFilter, ServletRegistrationBean h2Console) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .headers(httpSecurityHeadersConfigurer -> httpSecurityHeadersConfigurer.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request ->
                        request

                                .requestMatchers(jwtUtil.allowedUrls).permitAll()
                                .requestMatchers("/user").hasAnyAuthority("ADMIN")
                                .requestMatchers(antMatcher("/h2-console/**")).permitAll()
                                .requestMatchers(antMatcher(HttpMethod.GET, "/chargers/{chargerId}/users/{userId}")).permitAll()
                                .anyRequest().authenticated())
                .sessionManagement(sessionMangement ->
                        sessionMangement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .oauth2Login(configure ->
                        configure.authorizationEndpoint(config -> config.authorizationRequestRepository(httpCookieOAuth2AuthorizationRequestRepository))
                                .userInfoEndpoint(config -> config.userService(customOAuth2UserService))
                                .successHandler(oAuth2AuthenticationSuccessHandler)
                                .failureHandler(oAuth2AuthenticationFailureHandler))
                .addFilterBefore(jwtFilter, RequestCacheAwareFilter.class)
                .build();
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://ec2-43-203-7-98.ap-northeast-2.compute.amazonaws.com:8080","http://ec2-43-203-7-98.ap-northeast-2.compute.amazonaws.com:3000", "http://localhost:3000")
                .allowedMethods(
                        HttpMethod.GET.name(),
                        HttpMethod.PATCH.name(),
                        HttpMethod.PUT.name(),
                        HttpMethod.DELETE.name(),
                        HttpMethod.POST.name(),
                        HttpMethod.HEAD.name())
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3000);
    }
}
