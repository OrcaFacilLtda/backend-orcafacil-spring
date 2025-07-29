package com.orcafacil.api.interfaceadapter.request.provider;


import com.orcafacil.api.domain.category.Category;
import com.orcafacil.api.domain.company.Company;
import com.orcafacil.api.domain.user.User;
import jakarta.validation.constraints.NotNull;

public class CreateProviderRequest {
    @NotNull(message = "Id do úsuario é obrigatório")
    private User user;
    @NotNull(message = "Id da empresa é obrigatório")
    private Company company;

    @NotNull(message = "Id da categoria é obrigatório")
    private Category category;

    public User getUser() {return user;}

    public void setUser(User user) {this.user = user;}

    public Company getCompany() {return company;}

    public void setCompany(Company company) {this.company = company;}

    public Category getCategory() {return category;}

    public void setCategory(Category category) {this.category = category;}
}