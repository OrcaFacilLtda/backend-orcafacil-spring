package com.orcafacil.api.domain.service;

import com.orcafacil.api.domain.company.Company;
import com.orcafacil.api.domain.user.User;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

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
        this.requestDate = requestDate != null ? new Date(requestDate.getTime()) : null;
        this.technicalVisitDate = technicalVisitDate != null ? new Date(technicalVisitDate.getTime()) : null;
        this.visitConfirmed = visitConfirmed;
        this.negotiatedStartDate = negotiatedStartDate != null ? new Date(negotiatedStartDate.getTime()) : null;
        this.negotiatedEndDate = negotiatedEndDate != null ? new Date(negotiatedEndDate.getTime()) : null;
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
        return requestDate != null ? new Date(requestDate.getTime()) : null;
    }

    public Date getTechnicalVisitDate() {
        return technicalVisitDate != null ? new Date(technicalVisitDate.getTime()) : null;
    }

    public Boolean getVisitConfirmed() {
        return visitConfirmed;
    }

    public Date getNegotiatedStartDate() {
        return negotiatedStartDate != null ? new Date(negotiatedStartDate.getTime()) : null;
    }

    public Date getNegotiatedEndDate() {
        return negotiatedEndDate != null ? new Date(negotiatedEndDate.getTime()) : null;
    }

    public BigDecimal getLaborCost() {
        return laborCost;
    }

    public Boolean getBudgetFinalized() {
        return budgetFinalized;
    }

    // Métodos with
    public Service withUser(User newUser) {
        return new Service(id, newUser, company, description, serviceStatus, requestDate, technicalVisitDate,
                visitConfirmed, negotiatedStartDate, negotiatedEndDate, laborCost, budgetFinalized);
    }

    public Service withCompany(Company newCompany) {
        return new Service(id, user, newCompany, description, serviceStatus, requestDate, technicalVisitDate,
                visitConfirmed, negotiatedStartDate, negotiatedEndDate, laborCost, budgetFinalized);
    }

    public Service withDescription(String newDescription) {
        return new Service(id, user, company, newDescription, serviceStatus, requestDate, technicalVisitDate,
                visitConfirmed, negotiatedStartDate, negotiatedEndDate, laborCost, budgetFinalized);
    }

    public Service withServiceStatus(ServiceStatus newStatus) {
        return new Service(id, user, company, description, newStatus, requestDate, technicalVisitDate,
                visitConfirmed, negotiatedStartDate, negotiatedEndDate, laborCost, budgetFinalized);
    }

    public Service withRequestDate(Date newRequestDate) {
        return new Service(id, user, company, description, serviceStatus,
                newRequestDate != null ? new Date(newRequestDate.getTime()) : null,
                technicalVisitDate, visitConfirmed, negotiatedStartDate, negotiatedEndDate, laborCost, budgetFinalized);
    }

    public Service withTechnicalVisitDate(Date newTechnicalVisitDate) {
        return new Service(id, user, company, description, serviceStatus,
                requestDate,
                newTechnicalVisitDate != null ? new Date(newTechnicalVisitDate.getTime()) : null,
                visitConfirmed, negotiatedStartDate, negotiatedEndDate, laborCost, budgetFinalized);
    }

    public Service withVisitConfirmed(Boolean newVisitConfirmed) {
        return new Service(id, user, company, description, serviceStatus, requestDate, technicalVisitDate,
                newVisitConfirmed, negotiatedStartDate, negotiatedEndDate, laborCost, budgetFinalized);
    }

    public Service withNegotiatedStartDate(Date newStartDate) {
        return new Service(id, user, company, description, serviceStatus, requestDate, technicalVisitDate,
                visitConfirmed,
                newStartDate != null ? new Date(newStartDate.getTime()) : null,
                negotiatedEndDate, laborCost, budgetFinalized);
    }

    public Service withNegotiatedEndDate(Date newEndDate) {
        return new Service(id, user, company, description, serviceStatus, requestDate, technicalVisitDate,
                visitConfirmed, negotiatedStartDate,
                newEndDate != null ? new Date(newEndDate.getTime()) : null,
                laborCost, budgetFinalized);
    }

    public Service withLaborCost(BigDecimal newLaborCost) {
        return new Service(id, user, company, description, serviceStatus, requestDate, technicalVisitDate,
                visitConfirmed, negotiatedStartDate, negotiatedEndDate, newLaborCost, budgetFinalized);
    }

    public Service withBudgetFinalized(Boolean newBudgetFinalized) {
        return new Service(id, user, company, description, serviceStatus, requestDate, technicalVisitDate,
                visitConfirmed, negotiatedStartDate, negotiatedEndDate, laborCost, newBudgetFinalized);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Service)) return false;
        Service service = (Service) o;
        return Objects.equals(id, service.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
