package com.example.Spring_crud_project.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Central Spring Security configuration for application
 *
 * <p>Configures a stateless, JWT-based security setup:</p>
 * <ul>
 *     <li>CSRF is disabled (not needed for stateless REST APIs).</li>
 *     <li>Sessions are never created — each request is authenticated via JWT</li>
 *     <li>{@link JwtAuthenticationFilter} is inserted before the default
 *         {@link UsernamePasswordAuthenticationFilter}</li>
 *     <li>Custom 401 / 403 error responses are wired via the entry point and
 *         access-denied handler</li>
 * </ul>
 *
 * <p>Method-level security ({@code @PreAuthorize}, {@code @Secured}, etc.) is
 * enabled via {@link EnableMethodSecurity}.</p>
 */
@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final JwtAuthenticationEntryPoint authEntryPoint;
    private final CustomAccessDeniedHandler accessDeniedHandler;

    /**
     * Constructor injection for security components.
     *
     * @param jwtAuthFilter      filter that extracts and validates JWTs per request
     * @param authEntryPoint     handles unauthenticated requests (returns 401)
     * @param accessDeniedHandler handles insufficient-permission requests (returns 403)
     */
    public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter, JwtAuthenticationEntryPoint authEntryPoint, CustomAccessDeniedHandler accessDeniedHandler) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.authEntryPoint = authEntryPoint;
        this.accessDeniedHandler = accessDeniedHandler;
    }

    /**
     * Defines the HTTP security filter chain.
     *
     * <p>Authorization rules (in order of evaluation):</p>
     * <ol>
     *   <li>{@code GET /api/books/**} — public, no authentication required.</li>
     *   <li>{@code /auth/**} — public (login / registration endpoints).</li>
     *   <li>All other requests — authentication required.</li>
     * </ol>
     * @param http the {@link HttpSecurity} builder
     * @return the configured {@link SecurityFilterChain}
     * @throws Exception if configuration fails
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                    // PUBLIC (read-only endpoints)
                    .requestMatchers(HttpMethod.GET, "/api/books/**").permitAll()
                    // AUTH endpoints
                    .requestMatchers("/auth/**").permitAll()
                    // EVERYTHING ELSE (POST/PUT/DELETE/etc)
                    .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
            .exceptionHandling(ex -> ex
                .authenticationEntryPoint(authEntryPoint)
                .accessDeniedHandler(accessDeniedHandler)
            );

        return http.build();
    }

    /**
     * Provides a {@link BCryptPasswordEncoder} bean for hashing and verifying passwords.
     *
     * @return a Bcrypt-based {@link PasswordEncoder}
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    /**
     * Exposes the {@link AuthenticationManager} as a Spring bean so it can be
     * injected into the authentication controller for programmatic authentication.
     *
     * @param config Spring's {@link AuthenticationConfiguration}
     * @return the application's {@link AuthenticationManager}
     * @throws Exception if retrieval fails
     */
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
