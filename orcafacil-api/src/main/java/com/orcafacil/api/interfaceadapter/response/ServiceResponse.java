package com.orcafacil.api.interfaceadapter.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ServiceResponse(
        Integer id,
        UserResponse user,
        CompanyResponse company,
        String description,
        String serviceStatus,
        LocalDateTime requestDate,
        LocalDateTime technicalVisitDate,
        boolean clientVisitConfirmed,
        boolean providerVisitConfirmed,
        boolean clientDatesConfirmed,
        boolean providerDatesConfirmed,
        boolean clientMaterialsConfirmed,
        boolean providerMaterialsConfirmed,
        LocalDateTime negotiatedStartDate,
        LocalDateTime negotiatedEndDate,
        BigDecimal laborCost,
        boolean budgetFinalized
) {}
