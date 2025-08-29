package com.orcafacil.api.interfaceadapter.response;

public record CompanyResponse(
        Integer id,
        String cnpj,
        String name,
        String email,
        String phone
) {}