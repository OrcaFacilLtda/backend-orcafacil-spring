package com.orcafacil.api.domain.category;

import com.orcafacil.api.domain.exception.DomainException;

public class Category {
    private final Integer id;
    private final String name;
    private final String description;

    public Category(Integer id, String name, String description) {
        validateName(name);
        validateDescription(description);
        this.id = id;
        this.name = name;
        this.description = description;
    }

    private void validateName(String name) {
        if (isNullOrEmpty(name)) {
            throw new DomainException("Nome não pode ser vazio.");
        }
    }

    private void validateDescription(String description) {
        if (isNullOrEmpty(description)) {
            throw new DomainException("Descrição não pode ser vazia.");
        }
    }

    private boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }


    public String getDescription() {return description;}

    public Integer getId() {return id;}

    public String getName() {return name;}
}
