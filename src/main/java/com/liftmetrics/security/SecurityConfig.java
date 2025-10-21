package com.liftmetrics.security;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // wyłączenie CSRF (żeby POST z Postmana działał bez tokena)
                .csrf(AbstractHttpConfigurer::disable)
                // konfiguracja uprawnień dla endpointów
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/exercise/**").permitAll()
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}