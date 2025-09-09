package com.orcafacil.api.interfaceadapter.response;

import java.util.List;

public class AdminChartData {
    private List<String> userRegistrationLabels;
    private List<Long> userRegistrationData;
    private List<String> servicesByCategoryLabels;
    private List<Long> servicesByCategoryData;

    // Getters e Setters
    public List<String> getUserRegistrationLabels() { return userRegistrationLabels; }

    public void setUserRegistrationLabels(List<String> userRegistrationLabels) { this.userRegistrationLabels = userRegistrationLabels; }

    public List<Long> getUserRegistrationData() { return userRegistrationData; }

    public void setUserRegistrationData(List<Long> userRegistrationData) { this.userRegistrationData = userRegistrationData; }

    public List<String> getServicesByCategoryLabels() { return servicesByCategoryLabels; }

    public void setServicesByCategoryLabels(List<String> servicesByCategoryLabels) { this.servicesByCategoryLabels = servicesByCategoryLabels; }

    public List<Long> getServicesByCategoryData() { return servicesByCategoryData; }

    public void setServicesByCategoryData(List<Long> servicesByCategoryData) { this.servicesByCategoryData = servicesByCategoryData; }
}