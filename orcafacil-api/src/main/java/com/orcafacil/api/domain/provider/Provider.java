package com.orcafacil.api.domain.provider;

import com.orcafacil.api.domain.category.Category;
import com.orcafacil.api.domain.company.Company;
import com.orcafacil.api.domain.exception.DomainException;
import com.orcafacil.api.domain.user.User;

import java.util.Objects;

public class Provider {
    private final User user;
    private final Company company;
    private final Category category;

    public Provider(User user, Company company, Category category) {
        validateUser(user);
        validateCompany(company);
        validateCategory(category);

        this.user = user;
        this.company = company;
        this.category = category;
    }

    private void validateUser(User user) {
        if (user == null) {
            throw new DomainException("Usuário é obrigatório.");
        }
    }

    private void validateCompany(Company company) {
        if (company == null) {
            throw new DomainException("Empresa é obrigatória.");
        }
    }

    private void validateCategory(Category category) {
        if (category == null || category.getId() == null) {
            throw new DomainException("Categoria é obrigatória e deve ter um ID válido.");
        }
    }

    public User getUser() {
        return user;
    }

    public Company getCompany() {
        return company;
    }

    public Category getCategory() {
        return category;
    }

    // Métodos withX
    public Provider withUser(User newUser) {
        return new Provider(newUser, this.company, this.category);
    }

    public Provider withCompany(Company newCompany) {
        return new Provider(this.user, newCompany, this.category);
    }

    public Provider withCategory(Category newCategory) {
        return new Provider(this.user, this.company, newCategory);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Provider)) return false;
        Provider that = (Provider) o;
        return user.equals(that.user) &&
                company.equals(that.company) &&
                category.equals(that.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, company, category);
    }

    @Override
    public String toString() {
        return "Provider{" +
                "user=" + user +
                ", company=" + company +
                ", category=" + category +
                '}';
    }
}