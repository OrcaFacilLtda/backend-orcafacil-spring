package com.orcafacil.api.domain.provider;

import com.orcafacil.api.domain.category.Category;
import com.orcafacil.api.domain.company.Company;
import com.orcafacil.api.domain.exception.DomainException;
import com.orcafacil.api.domain.user.User;

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
        if (user == null || user.getId() == null) {
            throw new DomainException("Usuário é obrigatório e deve ter um ID válido.");
        }
    }

    private void validateCompany(Company company) {
        if (company == null || company.getId() == null) {
            throw new DomainException("Empresa é obrigatória e deve ter um ID válido.");
        }
    }

    private void validateCategory(Category category) {
        if (category == null || category.getId() == null) {
            throw new DomainException("Categoria é obrigatória e deve ter um ID válido.");
        }
    }

    public User getUser() {return user;}

    public Company getCompany() {return company;}

    public Category getCategory() {return category;}
}
