package com.orcafacil.api.interfaceadapter.response;
import java.util.List;

public class ProviderChartData {
    private List<String> servicesByMonthLabels;
    private List<Long> servicesByMonthData;
    private List<String> earningsByMonthLabels;
    private List<Double> earningsByMonthData;

    // Getters e setters
    public List<String> getServicesByMonthLabels() { return servicesByMonthLabels; }

    public void setServicesByMonthLabels(List<String> servicesByMonthLabels) { this.servicesByMonthLabels = servicesByMonthLabels; }

    public List<Long> getServicesByMonthData() { return servicesByMonthData; }

    public void setServicesByMonthData(List<Long> servicesByMonthData) { this.servicesByMonthData = servicesByMonthData; }

    public List<String> getEarningsByMonthLabels() { return earningsByMonthLabels; }

    public void setEarningsByMonthLabels(List<String> earningsByMonthLabels) { this.earningsByMonthLabels = earningsByMonthLabels; }

    public List<Double> getEarningsByMonthData() { return earningsByMonthData; }

    public void setEarningsByMonthData(List<Double> earningsByMonthData) { this.earningsByMonthData = earningsByMonthData; }
}