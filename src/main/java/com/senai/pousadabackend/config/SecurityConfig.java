package com.senai.pousadabackend.config;

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

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(withDefaults())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/clientes/**").hasAnyAuthority("ROLE_cliente-operacao", "ROLE_admin")
                        .requestMatchers(HttpMethod.GET, "/clientes/**").hasAnyAuthority("ROLE_cliente-visualizacao", "ROLE_admin")

                        .requestMatchers("/amenidades/**").hasAnyAuthority("ROLE_amenidade-operacao", "ROLE_admin")
                        .requestMatchers(HttpMethod.GET, "/amenidades/**").hasAnyAuthority("ROLE_amenidade-visualizacao")

                        .requestMatchers("/complementos/**").hasAnyAuthority("ROLE_complemento-operacao", "ROLE_admin")
                        .requestMatchers(HttpMethod.GET, "/complementos/**").hasAnyAuthority("ROLE_complemento-visualizacao", "ROLE_admin")

                        .requestMatchers("/cupons/**").hasAnyAuthority("ROLE_cupom-operacao", "ROLE_admin")
                        .requestMatchers(HttpMethod.GET, "/cupons/**").hasAnyAuthority("ROLE_cupom-visualizacao", "ROLE_admin")

                        .requestMatchers("/quartos/**").hasAnyAuthority("ROLE_quarto-operacao", "ROLE_admin")
                        .requestMatchers(HttpMethod.GET, "/quartos/**").hasAnyAuthority("ROLE_quarto-visualizacao", "ROLE_admin")

                        .requestMatchers("/reservas/**").hasAnyAuthority("ROLE_reserva-operacao", "ROLE_admin")
                        .requestMatchers(HttpMethod.GET, "/reservas/**").hasAnyAuthority("ROLE_reserva-visualizacao", "ROLE_admin")

                        .requestMatchers("/usuarios/**").hasAuthority("ROLE_admin")
                        .requestMatchers("/roles/**").hasAuthority("ROLE_admin")

                        .requestMatchers(HttpMethod.POST, "/auth/**").permitAll()
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
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
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

            extractRolesFromClaim(jwt, "realm_access", authorities);
            extractRolesFromClaim(jwt, "resource_access", authorities, "ypua-client-front");

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
