package com.innovisor.quickpos.profilemanagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Bean
    public WebSecurityCustomizer webSecurityConfigurer(){
        return (web) -> {
            web.ignoring().requestMatchers(
                    HttpMethod.POST,
                    "/public/**",
                    "/auth/**",
                    "/users"
            );

            web.ignoring().requestMatchers(
                    HttpMethod.GET,
                    "/public/**"
            );

            web.ignoring().requestMatchers(
                    HttpMethod.DELETE,
                    "/public/**",
                    "users/{id}"
            );

            web.ignoring().requestMatchers(
                    HttpMethod.PUT,
                    "/public/**",
                    "/users/{id}/send-verification-email",
                    "/users/forgot-password",
                    "/roles/assign/users/{userId}"
            );

            web.ignoring().requestMatchers(
                            HttpMethod.OPTIONS,
                            "/**"
                    )
                    .requestMatchers(
                            "/v3/api-docs/**",
                            "/configuration/**",
                            "/swagger-ui/**",
                            "swagger-resources/**",
                            "/swagger-ui.html",
                            "/webjars/**",
                            "/api-docs/**"
                    );
            ;
        };
    }
}
