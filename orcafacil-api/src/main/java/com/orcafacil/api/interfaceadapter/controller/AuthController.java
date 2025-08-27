package com.orcafacil.api.interfaceadapter.controller;

import com.orcafacil.api.interfaceadapter.request.auth.LoginRequest;
import com.orcafacil.api.infrastructure.security.jwt.TokenService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public AuthController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest loginRequest, HttpServletResponse response) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password())
            );

            String token = tokenService.generateToken(auth);

            Cookie cookie = new Cookie("auth-token", token);
            cookie.setHttpOnly(true);
            cookie.setSecure(false); // em produção: true
            cookie.setPath("/");
            cookie.setMaxAge(3600);

            response.addCookie(cookie);

            return ResponseEntity.ok().build();
        } catch (org.springframework.security.core.AuthenticationException ex) {
            return ResponseEntity.status(401).body("Email ou senha inválidos.");
        } catch (Exception ex) {
            return ResponseEntity.status(500).body("Erro interno ao processar login.");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("auth-token", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return ResponseEntity.ok().build();
    }
}
