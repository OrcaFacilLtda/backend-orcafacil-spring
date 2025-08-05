package com.orcafacil.api.interfaceadapter.request.provider;

import com.orcafacil.api.interfaceadapter.request.company.UpdateCompanyRequest;
import com.orcafacil.api.interfaceadapter.request.user.UserUpdateRequest;

import jakarta.validation.constraints.NotNull;

public class UpdateProviderRequest {

    @NotNull(message = "Dados do usuário são obrigatórios para atualização")
    private UserUpdateRequest userUpdateRequest;

    @NotNull(message = "Dados da empresa são obrigatórios para atualização")
    private UpdateCompanyRequest companyUpdateRequest;

    @NotNull(message = "Categoria é obrigatória")
    private Integer categoryId;

    public UserUpdateRequest getUserUpdateRequest() {
        return userUpdateRequest;
    }

    public void setUserUpdateRequest(UserUpdateRequest userUpdateRequest) {
        this.userUpdateRequest = userUpdateRequest;
    }

    public UpdateCompanyRequest getCompanyUpdateRequest() {
        return companyUpdateRequest;
    }

    public void setCompanyUpdateRequest(UpdateCompanyRequest companyUpdateRequest) {
        this.companyUpdateRequest = companyUpdateRequest;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
}
