package com.orcafacil.api.interfaceadapter.request.sevice;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ServiceRequest(
        @NotBlank
        String description,

        @NotNull
        UserIdDTO user,

        @NotNull
        CompanyIdDTO company
) {
    public record UserIdDTO(Long id) {}
    public record CompanyIdDTO(Long id) {}
}