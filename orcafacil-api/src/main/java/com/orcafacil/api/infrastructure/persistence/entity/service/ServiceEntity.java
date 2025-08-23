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
    private ServiceStatus status;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "request_date", nullable = false, updatable = false)
    private Date requestDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "technical_visit_date")
    private Date technicalVisitDate;

    // --- CAMPOS DE CONFIRMAÇÃO BILATERAL ADICIONADOS ---
    @Column(name = "client_visit_confirmed", nullable = false)
    private Boolean clientVisitConfirmed = false;

    @Column(name = "provider_visit_confirmed", nullable = false)
    private Boolean providerVisitConfirmed = false;

    @Column(name = "client_dates_confirmed", nullable = false)
    private Boolean clientDatesConfirmed = false;

    @Column(name = "provider_dates_confirmed", nullable = false)
    private Boolean providerDatesConfirmed = false;

    @Column(name = "client_materials_confirmed", nullable = false)
    private Boolean clientMaterialsConfirmed = false;

    @Column(name = "provider_materials_confirmed", nullable = false)
    private Boolean providerMaterialsConfirmed = false;
    // --- FIM DOS CAMPOS ADICIONADOS ---

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

    // Getters e Setters para todos os campos...
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

    public ServiceStatus getStatus() {
        return status;
    }

    public void setStatus(ServiceStatus status) {
        this.status = status;
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

    public Boolean getClientVisitConfirmed() {
        return clientVisitConfirmed;
    }

    public void setClientVisitConfirmed(Boolean clientVisitConfirmed) {
        this.clientVisitConfirmed = clientVisitConfirmed;
    }

    public Boolean getProviderVisitConfirmed() {
        return providerVisitConfirmed;
    }

    public void setProviderVisitConfirmed(Boolean providerVisitConfirmed) {
        this.providerVisitConfirmed = providerVisitConfirmed;
    }

    public Boolean getClientDatesConfirmed() {
        return clientDatesConfirmed;
    }

    public void setClientDatesConfirmed(Boolean clientDatesConfirmed) {
        this.clientDatesConfirmed = clientDatesConfirmed;
    }

    public Boolean getProviderDatesConfirmed() {
        return providerDatesConfirmed;
    }

    public void setProviderDatesConfirmed(Boolean providerDatesConfirmed) {
        this.providerDatesConfirmed = providerDatesConfirmed;
    }

    public Boolean getClientMaterialsConfirmed() {
        return clientMaterialsConfirmed;
    }

    public void setClientMaterialsConfirmed(Boolean clientMaterialsConfirmed) {
        this.clientMaterialsConfirmed = clientMaterialsConfirmed;
    }

    public Boolean getProviderMaterialsConfirmed() {
        return providerMaterialsConfirmed;
    }

    public void setProviderMaterialsConfirmed(Boolean providerMaterialsConfirmed) {
        this.providerMaterialsConfirmed = providerMaterialsConfirmed;
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