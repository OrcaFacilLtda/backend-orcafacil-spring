package com.orcafacil.api.interfaceadapter.response;

public class AdminDashboardStats {

    private long totalUsers;
    private long activeProviders;
    private long completedServicesThisMonth;

    // Getters e setters
    public long getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(long totalUsers) {
        this.totalUsers = totalUsers;
    }

    public long getActiveProviders() {
        return activeProviders;
    }

    public void setActiveProviders(long activeProviders) {
        this.activeProviders = activeProviders;
    }

    public long getCompletedServicesThisMonth() {
        return completedServicesThisMonth;
    }

    public void setCompletedServicesThisMonth(long completedServicesThisMonth) {
        this.completedServicesThisMonth = completedServicesThisMonth;
    }
}