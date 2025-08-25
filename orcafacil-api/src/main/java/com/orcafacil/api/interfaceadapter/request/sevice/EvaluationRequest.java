package com.orcafacil.api.interfaceadapter.request.sevice;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class EvaluationRequest {

    @NotNull(message = "A nota é obrigatória.")
    @Min(value = 0, message = "A nota deve ser no mínimo 0.")
    @Max(value = 5, message = "A nota deve ser no máximo 5.")
    private Integer stars;

    // Getters e Setters
    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }
}