package com.orcafacil.api.infrastructure.security.config;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import jakarta.servlet.http.Cookie;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.security.web.SecurityFilterChain;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // Mantemos para usar @PreAuthorize para regras complexas como a de status 'APPROVED'
public class SecurityConfig {

    private final RSAPublicKey rsaPublicKey;
    private final RSAPrivateKey rsaPrivateKey;

    public SecurityConfig(RsaKeyProperties properties, RsaKeyLoader loader) throws Exception {
        this.rsaPublicKey = loader.loadPublicKey(properties.getPublicKeyPath());
        this.rsaPrivateKey = loader.loadPrivateKey(properties.getPrivateKeyPath());
    }

    @Bean
    public BearerTokenResolver bearerTokenResolver() {
        return request -> {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                return Arrays.stream(cookies)
                        .filter(cookie -> "auth-token".equals(cookie.getName()))
                        .findFirst()
                        .map(Cookie::getValue)
                        .orElse(null);
            }
            return null;
        };
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        // --- ROTAS PÚBLICAS ---
                        .requestMatchers(HttpMethod.POST, "/login", "/logout").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/users").permitAll() // Cadastro de cliente
                        .requestMatchers(HttpMethod.POST, "/api/providers").permitAll() // Cadastro de prestador

                        // --- ROTAS DE ADMIN ---
                        .requestMatchers("/api/categories/**").hasAuthority("SCOPE_ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/api/users/{id}/status").hasAuthority("SCOPE_ADMIN")

                        // --- ROTAS DE PROVIDER ---
                        .requestMatchers("/api/providers/**").hasAuthority("SCOPE_PROVIDER")

                        // --- ROTAS DE CLIENT E PROVIDER ---
                        .requestMatchers("/api/services/**").hasAnyAuthority("SCOPE_CLIENT", "SCOPE_PROVIDER")
                        .requestMatchers("/api/users/**").hasAnyAuthority("SCOPE_CLIENT", "SCOPE_PROVIDER") // Demais rotas de users

                        // --- REGRA GERAL ---
                        .anyRequest().authenticated() // Qualquer outra rota exige autenticação
                )
                .csrf(csrf -> csrf.disable())
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(Customizer.withDefaults())
                        .bearerTokenResolver(bearerTokenResolver())
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public JwtEncoder jwtEncoder() {
        JWK jwk = new RSAKey.Builder(this.rsaPublicKey)
                .privateKey(this.rsaPrivateKey)
                .build();
        var jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(rsaPublicKey).build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
