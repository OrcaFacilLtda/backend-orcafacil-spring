package com.orcafacil.api.interfaceadapter.controller;

import com.orcafacil.api.application.service.stats.StatisticsService;
import com.orcafacil.api.interfaceadapter.response.ApiResponse;
import com.orcafacil.api.interfaceadapter.response.ProviderStats;
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

    @GetMapping("/provider/{companyId}")
    public ResponseEntity<ApiResponse<ProviderStats>> getProviderStats(@PathVariable Integer companyId) {
        ProviderStats stats = statisticsService.getProviderStats(companyId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Estatísticas do prestador recuperadas com sucesso.", stats));
    }
}