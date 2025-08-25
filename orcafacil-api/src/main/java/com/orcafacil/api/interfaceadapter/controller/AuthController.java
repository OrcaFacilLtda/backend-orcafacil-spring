package com.orcafacil.api.interfaceadapter.controller;

import com.orcafacil.api.infrastructure.security.jwt.TokenService; // <-- Pacote corrigido
import com.orcafacil.api.interfaceadapter.request.auth.LoginRequest;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    public AuthController(TokenService tokenService, AuthenticationManager authenticationManager) {
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password());

        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        String token = tokenService.generateToken(authentication);

        // Cria um cookie HttpOnly para armazenar o token de forma segura
        Cookie cookie = new Cookie("auth-token", token);
        cookie.setHttpOnly(true);      // Impede acesso via JavaScript (proteção XSS)
        cookie.setSecure(false);       // Em produção, mude para 'true' se usar HTTPS
        cookie.setPath("/");           // O cookie é válido para todo o site
        cookie.setMaxAge(60 * 60);     // Expira em 1 hora

        response.addCookie(cookie);

        return ResponseEntity.ok().build(); // Retorna 200 OK sem corpo
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        // Cria um cookie com o mesmo nome para invalidar o anterior
        Cookie cookie = new Cookie("auth-token", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(false); // Deve corresponder à configuração do login
        cookie.setPath("/");
        cookie.setMaxAge(0); // Expira imediatamente

        response.addCookie(cookie);

        return ResponseEntity.ok().build();
    }
}