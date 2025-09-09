package com.orcafacil.api.interfaceadapter.request.provider;

import com.orcafacil.api.interfaceadapter.request.company.CreateCompanyRequest;
import com.orcafacil.api.interfaceadapter.request.user.UserRequest;
import jakarta.validation.constraints.NotNull;

public class CreateProviderRequest {

    @NotNull(message = "Dados do usuário são obrigatórios")
    private UserRequest userRequest;

    @NotNull(message = "Dados da empresa são obrigatórios")
    private CreateCompanyRequest companyRequest;

    @NotNull(message = "Categoria é obrigatória")
    private Integer categoryId;

    // Getters e setters
    public UserRequest getUserRequest() { return userRequest; }

    public void setUserRequest(UserRequest userRequest) { this.userRequest = userRequest; }

    public CreateCompanyRequest getCompanyRequest() { return companyRequest; }

    public void setCompanyRequest(CreateCompanyRequest companyRequest) { this.companyRequest = companyRequest; }

    public Integer getCategoryId() { return categoryId; }

    public void setCategoryId(Integer categoryId) { this.categoryId = categoryId; }
}