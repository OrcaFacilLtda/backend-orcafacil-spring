package com.orcafacil.api.infrastructure.persistence.entity.service;

import com.orcafacil.api.domain.service.ServiceStatus;
import com.orcafacil.api.infrastructure.persistence.entity.company.CompanyEntity;
import com.orcafacil.api.infrastructure.persistence.entity.user.UserEntity;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "service")
public class ServiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private UserEntity client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private CompanyEntity company;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ServiceStatus serviceStatus;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "request_date", nullable = false)
    private Date requestDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "technical_visit_date")
    private Date technicalVisitDate;

    @Column(name = "visit_confirmed", nullable = false)
    private Boolean visitConfirmed = false;

    @Temporal(TemporalType.DATE)
    @Column(name = "negotiated_start_date")
    private Date negotiatedStartDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "negotiated_end_date")
    private Date negotiatedEndDate;

    @Column(name = "labor_cost", precision = 10, scale = 2)
    private BigDecimal laborCost;

    @Column(name = "budget_finalized", nullable = false)
    private Boolean budgetFinalized = false;

    // Construtor padrão necessário para JPA
    public ServiceEntity() {}

    // Construtor completo opcional
    public ServiceEntity(Integer id, UserEntity client, CompanyEntity company, String description,
                         ServiceStatus serviceStatus, Date requestDate, Date technicalVisitDate,
                         Boolean visitConfirmed, Date negotiatedStartDate, Date negotiatedEndDate,
                         BigDecimal laborCost, Boolean budgetFinalized) {
        this.id = id;
        this.client = client;
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

    // Getters e setters para JPA

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserEntity getClient() {
        return client;
    }

    public void setClient(UserEntity client) {
        this.client = client;
    }

    public CompanyEntity getCompany() {
        return company;
    }

    public void setCompany(CompanyEntity company) {
        this.company = company;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ServiceStatus getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(ServiceStatus serviceStatus) {
        this.serviceStatus = serviceStatus;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public Date getTechnicalVisitDate() {
        return technicalVisitDate;
    }

    public void setTechnicalVisitDate(Date technicalVisitDate) {
        this.technicalVisitDate = technicalVisitDate;
    }

    public Boolean getVisitConfirmed() {
        return visitConfirmed;
    }

    public void setVisitConfirmed(Boolean visitConfirmed) {
        this.visitConfirmed = visitConfirmed;
    }

    public Date getNegotiatedStartDate() {
        return negotiatedStartDate;
    }

    public void setNegotiatedStartDate(Date negotiatedStartDate) {
        this.negotiatedStartDate = negotiatedStartDate;
    }

    public Date getNegotiatedEndDate() {
        return negotiatedEndDate;
    }

    public void setNegotiatedEndDate(Date negotiatedEndDate) {
        this.negotiatedEndDate = negotiatedEndDate;
    }

    public BigDecimal getLaborCost() {
        return laborCost;
    }

    public void setLaborCost(BigDecimal laborCost) {
        this.laborCost = laborCost;
    }

    public Boolean getBudgetFinalized() {
        return budgetFinalized;
    }

    public void setBudgetFinalized(Boolean budgetFinalized) {
        this.budgetFinalized = budgetFinalized;
    }
}
