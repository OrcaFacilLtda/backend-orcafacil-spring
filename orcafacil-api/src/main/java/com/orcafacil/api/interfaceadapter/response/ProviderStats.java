package com.orcafacil.api.interfaceadapter.response;

import com.orcafacil.api.domain.service.Service;
import java.util.List;

public class ProviderStats {

    private long totalServices;
    private double acceptanceRate;
    private Double averageRating;
    private List<Service> newRequests;
    private List<Service> acceptedToday;
    private List<Service> pendingServices;

    // Construtores, Getters e Setters

    public ProviderStats() {}

    public long getTotalServices() {
        return totalServices;
    }

    public void setTotalServices(long totalServices) {
        this.totalServices = totalServices;
    }

    public double getAcceptanceRate() {
        return acceptanceRate;
    }

    public void setAcceptanceRate(double acceptanceRate) {
        this.acceptanceRate = acceptanceRate;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public List<Service> getNewRequests() {
        return newRequests;
    }

    public void setNewRequests(List<Service> newRequests) {
        this.newRequests = newRequests;
    }

    public List<Service> getAcceptedToday() {
        return acceptedToday;
    }

    public void setAcceptedToday(List<Service> acceptedToday) {
        this.acceptedToday = acceptedToday;
    }

    public List<Service> getPendingServices() {
        return pendingServices;
    }

    public void setPendingServices(List<Service> pendingServices) {
        this.pendingServices = pendingServices;
    }
}