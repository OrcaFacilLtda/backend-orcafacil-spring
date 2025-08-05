package com.orcafacil.api.interfaceadapter.request.company;

import com.orcafacil.api.interfaceadapter.request.address.AddressRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;

public class CreateCompanyRequest {

    @NotBlank(message = "Razão social é obrigatória")
    @Size(max = 150, message = "Razão social deve ter no máximo 150 caracteres")
    private String legalName;

    @NotBlank(message = "CNPJ é obrigatório")
    @Pattern(regexp = "\\d{14}", message = "CNPJ deve conter exatamente 14 dígitos")
    private String cnpj;

    @NotNull(message = "Endereço é obrigatório")
    private AddressRequest address;

    // Getters e Setters
    public String getLegalName() { return legalName; }
    public void setLegalName(String legalName) { this.legalName = legalName; }

    public String getCnpj() { return cnpj; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }

    public AddressRequest getAddress() { return address; }
    public void setAddress(AddressRequest address) { this.address = address; }
}