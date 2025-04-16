package com.example.bookstore.Configuration;

import com.example.bookstore.DTO.OrderRequestDTO;
import com.example.bookstore.Repository.OrderRepository;
import com.example.bookstore.Security.JwtFilter;
import com.example.bookstore.Service.UserdetailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class AppConfig {

    @Autowired
    @Lazy
    private JwtFilter jwtFilter;

    @Autowired
    private UserdetailService userDetailsService;

    @Bean
    public SecurityFilterChain ownsecurity(HttpSecurity http) throws Exception {
        return http
            .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Enable CORS globally
            .csrf(csrf -> csrf.disable()) // Disable CSRF when using JWT (cross-site request forgery)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() // Allow preflight requests (CORS)
                .requestMatchers("/admin/**").hasRole("ADMIN") // Admin routes
                .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN") // User routes
                .requestMatchers("/seller/**").hasAnyRole("SELLER", "ADMIN") // Seller routes
                .requestMatchers("/api/payments/webhook").permitAll() // Payment webhook is public
                .requestMatchers("/api/payments/process").authenticated() // Payments process needs authentication
                .requestMatchers("/getCsrf", "/registoration", "/login", "/AllBooks").permitAll() // Public routes
                .anyRequest().authenticated() // All other routes require authentication
            )
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Stateless session
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class) // Add JWT filter before authentication
            .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        // Add your allowed origins (this should match your front-end URLs)
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000", "https://bookstore-app-ten-zeta.vercel.app"));
        
        // Allow common HTTP methods and headers for CORS
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "X-CSRF-Token"));
        configuration.setExposedHeaders(Arrays.asList("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true); // Allow credentials (cookies, authentication tokens, etc.)
        
        // Register the configuration for all endpoints
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12)); // Set the password encoder for bcrypt
        provider.setUserDetailsService(userDetailsService); // Use the custom UserdetailService
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
    
    @Bean
    public OrderRequestDTO orderRequestDTO() {
        return new OrderRequestDTO(); // Order request DTO bean
    }
}
