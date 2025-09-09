package com.orcafacil.api.infrastructure.persistence.entity.budgetrevisionrequest;

import com.orcafacil.api.infrastructure.persistence.entity.service.ServiceEntity;
import com.orcafacil.api.infrastructure.persistence.entity.user.UserEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "budget_revision_request")
public class BudgetRevisionRequestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "service_id", nullable = false)
    private ServiceEntity service;

    @ManyToOne(optional = false)
    @JoinColumn(name = "client_id", nullable = false)
    private UserEntity client;

    @Column(name = "request_date", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime requestDate;

    public BudgetRevisionRequestEntity() {
    }

    public BudgetRevisionRequestEntity(Integer id, ServiceEntity service, UserEntity client, LocalDateTime requestDate) {
        this.id = id;
        this.service = service;
        this.client = client;
        this.requestDate = requestDate;
    }

    @PrePersist
    protected void onCreate() {
        if (requestDate == null) {
            requestDate = LocalDateTime.now();
        }
    }

    // Getters e setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ServiceEntity getService() {
        return service;
    }

    public void setService(ServiceEntity service) {
        this.service = service;
    }

    public UserEntity getClient() {
        return client;
    }

    public void setClient(UserEntity client) {
        this.client = client;
    }

    public LocalDateTime getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDateTime requestDate) {
        this.requestDate = requestDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BudgetRevisionRequestEntity)) return false;
        BudgetRevisionRequestEntity that = (BudgetRevisionRequestEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(service, that.service) &&
                Objects.equals(client, that.client) &&
                Objects.equals(requestDate, that.requestDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, service, client, requestDate);
    }

    @Override
    public String toString() {
        return "BudgetRevisionRequestEntity{" +
                "id=" + id +
                ", service=" + service +
                ", client=" + client +
                ", requestDate=" + requestDate +
                '}';
    }
}
