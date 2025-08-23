package com.orcafacil.api.infrastructure.security.validator;

import com.orcafacil.api.domain.user.User;
import com.orcafacil.api.domain.user.UserRepository;
import com.orcafacil.api.domain.user.UserStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component("userStatusValidator")
public class UserStatusValidator {

    private final UserRepository userRepository;

    public UserStatusValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     *
     * @param authentication O objeto de autenticação do Spring Security.
     * @return true se o usuário estiver aprovado, false caso contrário.
     */
    public boolean isApproved(Authentication authentication) {
        if (authentication == null || !(authentication.getPrincipal() instanceof Jwt)) {
            return false;
        }

        Jwt jwt = (Jwt) authentication.getPrincipal();
        String userEmail = jwt.getSubject(); // O "subject" do token JWT é o email do usuário

        User user = userRepository.findByEmail(userEmail)
                .orElse(null);

        return user != null && user.getStatus() == UserStatus.APPROVED;
    }
}