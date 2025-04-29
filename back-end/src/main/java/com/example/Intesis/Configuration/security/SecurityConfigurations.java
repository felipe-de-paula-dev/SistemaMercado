package com.example.Intesis.Configuration.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {
    @Autowired
    private SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .cors(cors -> cors
                        .configurationSource(request -> {
                            var corsConfig = new org.springframework.web.cors.CorsConfiguration();
                            corsConfig.setAllowedOrigins(List.of("https://sistemamercado-intesis.up.railway.app"));
                            corsConfig.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                            corsConfig.setAllowedHeaders(List.of("*"));
                            corsConfig.setAllowCredentials(true);
                            return corsConfig;
                        })
                )
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(autorize -> autorize
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/registrar").permitAll()

                        .requestMatchers(HttpMethod.POST, "/api/venda").hasRole("EMPLOYEE")
                        .requestMatchers(HttpMethod.GET, "/api/venda").hasRole("EMPLOYEE")
                        .requestMatchers(HttpMethod.GET, "/api/venda/cliente").hasRole("EMPLOYEE")
                        .requestMatchers(HttpMethod.GET, "/api/venda/cliente/**").hasRole("EMPLOYEE")
                        .requestMatchers(HttpMethod.GET, "/api/cliente").hasRole("EMPLOYEE")
                        .requestMatchers(HttpMethod.GET, "/api/produto").hasRole("EMPLOYEE")
                        .requestMatchers(HttpMethod.GET, "/api/item").hasRole("EMPLOYEE")

                        .requestMatchers(HttpMethod.GET, "/api/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,  "/api/**").hasRole("ADMIN")


                        .requestMatchers(HttpMethod.GET, "/auth/token/validar").authenticated()
                        .requestMatchers(HttpMethod.GET, "/auth/token/roles").authenticated()

                        .requestMatchers(HttpMethod.GET, "/excel/clientes/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/excel/vendas/**").authenticated()

                        .requestMatchers(HttpMethod.GET, "/auth/user/search").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/cliente/search").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/produto/search").authenticated()

                        .requestMatchers(HttpMethod.GET, "/ping").permitAll()

                        .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
