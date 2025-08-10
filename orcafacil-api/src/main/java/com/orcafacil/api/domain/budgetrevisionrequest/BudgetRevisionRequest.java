package com.orcafacil.api.domain.budgetrevisionrequest;

import com.orcafacil.api.domain.service.Service;
import com.orcafacil.api.domain.user.User;

import java.time.LocalDateTime;
import java.util.Objects;

public class BudgetRevisionRequest {

    private final Integer id;
    private final Service service;
    private final User client;
    private final LocalDateTime requestDate;

    public BudgetRevisionRequest(Integer id, Service service, User client, LocalDateTime requestDate ) {
        this.id = id;
        this.service = service;
        this.client = client;
        this.requestDate = requestDate;
    }

    public Integer getId() {
        return id;
    }

    public Service getService() {
        return service;
    }

    public User getClient() {
        return client;
    }

    public LocalDateTime getRequestDate() {
        return requestDate;
    }


    // Métodos withX
    public BudgetRevisionRequest withId(Integer newId) {
        return new BudgetRevisionRequest(newId, service, client, requestDate);
    }

    public BudgetRevisionRequest withService(Service newService) {
        return new BudgetRevisionRequest(id, newService, client, requestDate);
    }

    public BudgetRevisionRequest withClient(User newClient) {
        return new BudgetRevisionRequest(id, service, newClient, requestDate);
    }

    public BudgetRevisionRequest withRequestDate(LocalDateTime newRequestDate) {
        return new BudgetRevisionRequest(id, service, client, newRequestDate);
    }


    @Override
    public String toString() {
        return "BudgetRevisionRequest{" +
                "id=" + id +
                ", service=" + service +
                ", client=" + client +
                ", requestDate=" + requestDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BudgetRevisionRequest)) return false;
        BudgetRevisionRequest that = (BudgetRevisionRequest) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(service, that.service) &&
                Objects.equals(client, that.client) &&
                Objects.equals(requestDate, that.requestDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, service, client, requestDate);
    }
}
