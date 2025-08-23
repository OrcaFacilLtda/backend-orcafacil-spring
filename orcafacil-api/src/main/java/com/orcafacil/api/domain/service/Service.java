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

    // Confirmações bilaterais
    private final Boolean clientVisitConfirmed;
    private final Boolean providerVisitConfirmed;
    private final Boolean clientDatesConfirmed;
    private final Boolean providerDatesConfirmed;
    private final Boolean clientMaterialsConfirmed;
    private final Boolean providerMaterialsConfirmed;

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
            Boolean clientVisitConfirmed,
            Boolean providerVisitConfirmed,
            Boolean clientDatesConfirmed,
            Boolean providerDatesConfirmed,
            Boolean clientMaterialsConfirmed,
            Boolean providerMaterialsConfirmed,
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
        this.requestDate = requestDate != null ? new Date(requestDate.getTime()) : null;
        this.technicalVisitDate = technicalVisitDate != null ? new Date(technicalVisitDate.getTime()) : null;
        this.clientVisitConfirmed = clientVisitConfirmed;
        this.providerVisitConfirmed = providerVisitConfirmed;
        this.clientDatesConfirmed = clientDatesConfirmed;
        this.providerDatesConfirmed = providerDatesConfirmed;
        this.clientMaterialsConfirmed = clientMaterialsConfirmed;
        this.providerMaterialsConfirmed = providerMaterialsConfirmed;
        this.negotiatedStartDate = negotiatedStartDate != null ? new Date(negotiatedStartDate.getTime()) : null;
        this.negotiatedEndDate = negotiatedEndDate != null ? new Date(negotiatedEndDate.getTime()) : null;
        this.laborCost = laborCost;
        this.budgetFinalized = budgetFinalized;
    }

    // Getters
    public Integer getId() { return id; }
    public User getUser() { return user; }
    public Company getCompany() { return company; }
    public String getDescription() { return description; }
    public ServiceStatus getServiceStatus() { return serviceStatus; }
    public Date getRequestDate() { return requestDate; }
    public Date getTechnicalVisitDate() { return technicalVisitDate; }
    public Boolean getClientVisitConfirmed() { return clientVisitConfirmed; }
    public Boolean getProviderVisitConfirmed() { return providerVisitConfirmed; }
    public Boolean getClientDatesConfirmed() { return clientDatesConfirmed; }
    public Boolean getProviderDatesConfirmed() { return providerDatesConfirmed; }
    public Boolean getClientMaterialsConfirmed() { return clientMaterialsConfirmed; }
    public Boolean getProviderMaterialsConfirmed() { return providerMaterialsConfirmed; }
    public Date getNegotiatedStartDate() { return negotiatedStartDate; }
    public Date getNegotiatedEndDate() { return negotiatedEndDate; }
    public BigDecimal getLaborCost() { return laborCost; }
    public Boolean getBudgetFinalized() { return budgetFinalized; }

    // Métodos with (imutáveis)
    public Service withServiceStatus(ServiceStatus newStatus) {
        return new Service(id, user, company, description, newStatus, requestDate, technicalVisitDate,
                clientVisitConfirmed, providerVisitConfirmed, clientDatesConfirmed, providerDatesConfirmed,
                clientMaterialsConfirmed, providerMaterialsConfirmed, negotiatedStartDate, negotiatedEndDate,
                laborCost, budgetFinalized);
    }

    public Service withClientVisitConfirmed(Boolean value) {
        return new Service(id, user, company, description, serviceStatus, requestDate, technicalVisitDate,
                value, providerVisitConfirmed, clientDatesConfirmed, providerDatesConfirmed,
                clientMaterialsConfirmed, providerMaterialsConfirmed, negotiatedStartDate, negotiatedEndDate,
                laborCost, budgetFinalized);
    }

    public Service withProviderVisitConfirmed(Boolean value) {
        return new Service(id, user, company, description, serviceStatus, requestDate, technicalVisitDate,
                clientVisitConfirmed, value, clientDatesConfirmed, providerDatesConfirmed,
                clientMaterialsConfirmed, providerMaterialsConfirmed, negotiatedStartDate, negotiatedEndDate,
                laborCost, budgetFinalized);
    }

    public Service withClientDatesConfirmed(Boolean value) {
        return new Service(id, user, company, description, serviceStatus, requestDate, technicalVisitDate,
                clientVisitConfirmed, providerVisitConfirmed, value, providerDatesConfirmed,
                clientMaterialsConfirmed, providerMaterialsConfirmed, negotiatedStartDate, negotiatedEndDate,
                laborCost, budgetFinalized);
    }

    public Service withProviderDatesConfirmed(Boolean value) {
        return new Service(id, user, company, description, serviceStatus, requestDate, technicalVisitDate,
                clientVisitConfirmed, providerVisitConfirmed, clientDatesConfirmed, value,
                clientMaterialsConfirmed, providerMaterialsConfirmed, negotiatedStartDate, negotiatedEndDate,
                laborCost, budgetFinalized);
    }

    public Service withClientMaterialsConfirmed(Boolean value) {
        return new Service(id, user, company, description, serviceStatus, requestDate, technicalVisitDate,
                clientVisitConfirmed, providerVisitConfirmed, clientDatesConfirmed, providerDatesConfirmed,
                value, providerMaterialsConfirmed, negotiatedStartDate, negotiatedEndDate,
                laborCost, budgetFinalized);
    }

    public Service withProviderMaterialsConfirmed(Boolean value) {
        return new Service(id, user, company, description, serviceStatus, requestDate, technicalVisitDate,
                clientVisitConfirmed, providerVisitConfirmed, clientDatesConfirmed, providerDatesConfirmed,
                clientMaterialsConfirmed, value, negotiatedStartDate, negotiatedEndDate,
                laborCost, budgetFinalized);
    }

    public Service withNegotiatedStartDate(Date start) {
        return new Service(id, user, company, description, serviceStatus, requestDate, technicalVisitDate,
                clientVisitConfirmed, providerVisitConfirmed, clientDatesConfirmed, providerDatesConfirmed,
                clientMaterialsConfirmed, providerMaterialsConfirmed, start, negotiatedEndDate,
                laborCost, budgetFinalized);
    }

    public Service withNegotiatedEndDate(Date end) {
        return new Service(id, user, company, description, serviceStatus, requestDate, technicalVisitDate,
                clientVisitConfirmed, providerVisitConfirmed, clientDatesConfirmed, providerDatesConfirmed,
                clientMaterialsConfirmed, providerMaterialsConfirmed, negotiatedStartDate, end,
                laborCost, budgetFinalized);
    }

    public Service withLaborCost(BigDecimal cost) {
        return new Service(id, user, company, description, serviceStatus, requestDate, technicalVisitDate,
                clientVisitConfirmed, providerVisitConfirmed, clientDatesConfirmed, providerDatesConfirmed,
                clientMaterialsConfirmed, providerMaterialsConfirmed, negotiatedStartDate, negotiatedEndDate,
                cost, budgetFinalized);
    }

    public Service withBudgetFinalized(Boolean finalized) {
        return new Service(id, user, company, description, serviceStatus, requestDate, technicalVisitDate,
                clientVisitConfirmed, providerVisitConfirmed, clientDatesConfirmed, providerDatesConfirmed,
                clientMaterialsConfirmed, providerMaterialsConfirmed, negotiatedStartDate, negotiatedEndDate,
                laborCost, finalized);
    }
}
