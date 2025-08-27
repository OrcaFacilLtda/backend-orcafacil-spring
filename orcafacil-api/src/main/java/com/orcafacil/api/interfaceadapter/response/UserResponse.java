package com.orcafacil.api.interfaceadapter.response;

import com.orcafacil.api.domain.user.UserType;
import com.orcafacil.api.domain.user.UserStatus;

import java.util.Date;

public record UserResponse(
        Integer id,
        String name,
        String email,
        String phone,
        String cpf,
        UserType userType,
        UserStatus status,
        Date birthDate
) {}
