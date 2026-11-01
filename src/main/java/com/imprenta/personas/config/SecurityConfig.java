package com.imprenta.personas.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer; // <-- IMPORTANTE AÑADIR
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration; // <-- IMPORTANTE AÑADIR
import org.springframework.web.cors.CorsConfigurationSource; // <-- IMPORTANTE AÑADIR
import org.springframework.web.cors.UrlBasedCorsConfigurationSource; // <-- IMPORTANTE AÑADIR

import java.util.List; // <-- IMPORTANTE AÑADIR

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .cors(Customizer.withDefaults()) // <-- 1. ACTIVAR CORS AQUÍ
            .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());

        return http.build();
    }

    // <-- 2. AÑADIR ESTE BEAN PARA CONFIGURAR LOS ORÍGENES PERMITIDOS
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // Permite el origen del frontend local de tu compañero
        configuration.setAllowedOrigins(List.of("http://localhost:3000"));
        // Permite los métodos necesarios para la API
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        // Permite cualquier cabecera
        configuration.setAllowedHeaders(List.of("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}