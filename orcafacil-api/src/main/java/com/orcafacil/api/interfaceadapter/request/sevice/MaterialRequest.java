package com.orcafacil.api.interfaceadapter.request.sevice;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.List;

public record MaterialRequest(
        @NotNull
        List<MaterialItem> materials
) {
        public record MaterialItem(
                @NotBlank(message = "O nome do material não pode ser vazio.")
                String name,

                @NotNull(message = "A quantidade não pode ser nula.")
                @Positive(message = "A quantidade deve ser positiva.")
                Integer quantity,

                @NotNull(message = "O preço unitário não pode ser nulo.")
                @Positive(message = "O preço unitário deve ser positivo.")
                BigDecimal unitPrice
        ) {}
}