package com.senai.pousadabackend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/clientes/**").hasAuthority("ROLE_cliente-operacao")
                        .requestMatchers(HttpMethod.PUT, "/clientes/**").hasAuthority("ROLE_cliente-operacao")
                        .requestMatchers(HttpMethod.DELETE, "/clientes/**").hasAuthority("ROLE_cliente-operacao")
                        .requestMatchers(HttpMethod.GET, "/clientes/**").hasAuthority("ROLE_cliente-visualizacao")

                        .requestMatchers(HttpMethod.POST, "/amenidades/**").hasAuthority("ROLE_amenidade-operacao")
                        .requestMatchers(HttpMethod.PUT, "/amenidades/**").hasAuthority("ROLE_amenidade-operacao")
                        .requestMatchers(HttpMethod.DELETE, "/amenidades/**").hasAuthority("ROLE_amenidade-operacao")
                        .requestMatchers(HttpMethod.GET, "/amenidades/**").hasAuthority("ROLE_amenidade-visualizacao")

                        .requestMatchers(HttpMethod.POST, "/complementos/**").hasAuthority("ROLE_complemento-operacao")
                        .requestMatchers(HttpMethod.PUT, "/complementos/**").hasAuthority("ROLE_complemento-operacao")
                        .requestMatchers(HttpMethod.DELETE, "/complementos/**").hasAuthority("ROLE_complemento-operacao")
                        .requestMatchers(HttpMethod.GET, "/complementos/**").hasAuthority("ROLE_complemento-visualizacao")

                        .requestMatchers(HttpMethod.POST, "/cupons/**").hasAuthority("ROLE_cupom-operacao")
                        .requestMatchers(HttpMethod.PUT, "/cupons/**").hasAuthority("ROLE_cupom-operacao")
                        .requestMatchers(HttpMethod.DELETE, "/cupons/**").hasAuthority("ROLE_cupom-operacao")
                        .requestMatchers(HttpMethod.GET, "/cupons/**").hasAuthority("ROLE_cupom-visualizacao")

                        .requestMatchers(HttpMethod.POST, "/quartos/**").hasAuthority("ROLE_quarto-operacao")
                        .requestMatchers(HttpMethod.PUT, "/quartos/**").hasAuthority("ROLE_quarto-operacao")
                        .requestMatchers(HttpMethod.DELETE, "/quartos/**").hasAuthority("ROLE_quarto-operacao")
                        .requestMatchers(HttpMethod.GET, "/quartos/**").hasAuthority("ROLE_quarto-visualizacao")

                        .requestMatchers(HttpMethod.POST, "/reservas/**").hasAuthority("ROLE_reserva-operacao")
                        .requestMatchers(HttpMethod.PUT, "/reservas/**").hasAuthority("ROLE_reserva-operacao")
                        .requestMatchers(HttpMethod.DELETE, "/reservas/**").hasAuthority("ROLE_reserva-operacao")
                        .requestMatchers(HttpMethod.GET, "/reservas/**").hasAuthority("ROLE_reserva-visualizacao")

                        .requestMatchers(HttpMethod.POST, "/auth").permitAll()
                        .requestMatchers(HttpMethod.POST, "/usuarios/**").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth ->
                        oauth.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())))
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(List.of("http://localhost:5173", "http://localhost:4200"));
        corsConfiguration.setAllowedMethods(List.of("GET", "POST"));
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedHeaders(List.of("*"));
        corsConfiguration.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwt ->  {
            Collection<GrantedAuthority> authorities = new ArrayList<>();

            extractRolesFromClaim(jwt, "real_access", authorities);
            extractRolesFromClaim(jwt, "resource_access", authorities, "quinta-ypua");

            return authorities;
        });

        return jwtAuthenticationConverter;
    }

    private void extractRolesFromClaim(Jwt jwt, String claimName, Collection<GrantedAuthority> authorities) {
        extractRolesFromClaim(jwt, claimName, authorities, null);
    }

    private void extractRolesFromClaim(Jwt jwt, String claimName, Collection<GrantedAuthority> authorities, String resource) {
        Map<String, Object> claim = jwt.getClaim(claimName);
        if (claim != null && claim.containsKey("roles")) {
            List<String> roles = (List<String>) claim.get("roles");
            for (String role : roles) {
                authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
            }
        }

        if (resource != null) {
            Map<String, Object> resourceAccess = jwt.getClaim(claimName);
            if (resourceAccess != null && resourceAccess.containsKey(resource)) {
                List<String> resourceRoles = (List<String>) ((Map<String, Object>) resourceAccess.get(resource)).get("roles");
                if (resourceRoles != null) {
                    for (String role : resourceRoles) {
                        authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
                    }
                }
            }
        }
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}