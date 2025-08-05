package com.orcafacil.api.interfaceadapter.request.company;

import com.orcafacil.api.interfaceadapter.request.address.AddressRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UpdateCompanyRequest {

    @NotBlank(message = "ID da empresa é obrigatório")
    private Integer id;

    @NotBlank(message = "Razão social é obrigatória")
    @Size(max = 150)
    private String legalName;

    @NotBlank(message = "CNPJ é obrigatório")
    @Pattern(regexp = "\\d{14}", message = "CNPJ deve conter 14 dígitos numéricos")
    private String cnpj;

    private AddressRequest address;

    // Getters e Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getLegalName() { return legalName; }
    public void setLegalName(String legalName) { this.legalName = legalName; }

    public String getCnpj() { return cnpj; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }

    public AddressRequest getAddress() { return address; }
    public void setAddress(AddressRequest address) { this.address = address; }
}