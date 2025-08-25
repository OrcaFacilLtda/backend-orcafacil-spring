package com.orcafacil.api.interfaceadapter.controller;

import com.orcafacil.api.application.service.stats.StatisticsService;
import com.orcafacil.api.interfaceadapter.response.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {

    private final StatisticsService statisticsService;

    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    // --- ENDPOINTS DO ADMINISTRADOR ---

    @GetMapping("/admin")
    public ResponseEntity<ApiResponse<AdminDashboardStats>> getAdminDashboardStats() {
        AdminDashboardStats stats = statisticsService.getAdminDashboardStats();
        return ResponseEntity.ok(new ApiResponse<>(true, "Estatísticas do dashboard do admin recuperadas.", stats));
    }

    @GetMapping("/admin/charts")
    public ResponseEntity<ApiResponse<AdminChartData>> getAdminChartData() {
        AdminChartData chartData = statisticsService.getAdminChartData();
        return ResponseEntity.ok(new ApiResponse<>(true, "Dados dos gráficos do admin recuperados.", chartData));
    }

    // --- ENDPOINTS DO PRESTADOR ---

    @GetMapping("/provider/{companyId}")
    public ResponseEntity<ApiResponse<ProviderStats>> getProviderStats(@PathVariable Integer companyId) {
        ProviderStats stats = statisticsService.getProviderStats(companyId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Estatísticas do prestador recuperadas com sucesso.", stats));
    }

    @GetMapping("/provider/{companyId}/charts")
    public ResponseEntity<ApiResponse<ProviderChartData>> getProviderChartData(@PathVariable Integer companyId) {
        ProviderChartData chartData = statisticsService.getProviderChartData(companyId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Dados dos gráficos do prestador recuperados.", chartData));
    }
}