package com.orcafacil.api.domain.service;

import com.orcafacil.api.domain.company.Company;
import com.orcafacil.api.domain.user.User;

import java.math.BigDecimal;
import java.util.Date;

public final class Service {
    private final Integer id;
    private final User user;
    private final Company company;
    private final String description;
    private final ServiceStatus serviceStatus;
    private final Date requestDate;
    private final Date technicalVisitDate;
    private final Boolean visitConfirmed;
    private final Date negotiatedStartDate;
    private final Date negotiatedEndDate;
    private final BigDecimal laborCost;
    private final Boolean budgetFinalized;

    public Service(
            Integer id,
            User user,
            Company company,
            String description,
            ServiceStatus serviceStatus,
            Date requestDate,
            Date technicalVisitDate,
            Boolean visitConfirmed,
            Date negotiatedStartDate,
            Date negotiatedEndDate,
            BigDecimal laborCost,
            Boolean budgetFinalized
    ) {
        this.id = id;
        this.user = user;
        this.company = company;
        this.description = description;
        this.serviceStatus = serviceStatus;
        this.requestDate = requestDate;
        this.technicalVisitDate = technicalVisitDate;
        this.visitConfirmed = visitConfirmed;
        this.negotiatedStartDate = negotiatedStartDate;
        this.negotiatedEndDate = negotiatedEndDate;
        this.laborCost = laborCost;
        this.budgetFinalized = budgetFinalized;
    }

    // Getters
    public Integer getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Company getCompany() {
        return company;
    }

    public String getDescription() {
        return description;
    }

    public ServiceStatus getServiceStatus() {
        return serviceStatus;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public Date getTechnicalVisitDate() {
        return technicalVisitDate;
    }

    public Boolean getVisitConfirmed() {
        return visitConfirmed;
    }

    public Date getNegotiatedStartDate() {
        return negotiatedStartDate;
    }

    public Date getNegotiatedEndDate() {
        return negotiatedEndDate;
    }

    public BigDecimal getLaborCost() {
        return laborCost;
    }

    public Boolean getBudgetFinalized() {
        return budgetFinalized;
    }
}
