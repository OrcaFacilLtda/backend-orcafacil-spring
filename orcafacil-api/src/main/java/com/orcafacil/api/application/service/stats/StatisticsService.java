package com.orcafacil.api.application.service.stats;

import com.orcafacil.api.domain.service.Service;
import com.orcafacil.api.domain.service.ServiceStatus;
import com.orcafacil.api.infrastructure.persistence.jpa.service.SpringDataServiceRepository;
import com.orcafacil.api.infrastructure.persistence.mapper.service.ServiceMapper;
import com.orcafacil.api.interfaceadapter.response.ProviderStats;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
public class StatisticsService {

    private final SpringDataServiceRepository serviceRepository;
    private final ServiceMapper serviceMapper;

    public StatisticsService(SpringDataServiceRepository serviceRepository, ServiceMapper serviceMapper) {
        this.serviceRepository = serviceRepository;
        this.serviceMapper = serviceMapper;
    }

    @Transactional(readOnly = true)
    public ProviderStats getProviderStats(Integer companyId) {
        ProviderStats stats = new ProviderStats();

        // 1. Total de serviços
        long totalServices = serviceRepository.countByCompanyId(companyId);
        stats.setTotalServices(totalServices);

        // 2. Taxa de aceitação
        List<ServiceStatus> excludedStatuses = Arrays.asList(ServiceStatus.REJECTED, ServiceStatus.REQUEST_SENT);
        long acceptedServices = serviceRepository.countByCompanyIdAndStatusNotIn(companyId, excludedStatuses);

        if (totalServices > 0) {
            double acceptanceRate = ((double) acceptedServices / totalServices) * 100;
            stats.setAcceptanceRate(acceptanceRate);
        } else {
            stats.setAcceptanceRate(0.0);
        }

        // 3. Média de avaliações
        Double averageRating = serviceRepository.findAverageRatingByCompanyId(companyId);
        stats.setAverageRating(averageRating);

        // 4. Novas solicitações (REQUEST_SENT)
        List<Service> newRequests = serviceRepository.findByCompanyIdAndStatus(companyId, ServiceStatus.REQUEST_SENT)
                .stream()
                .map(serviceMapper::toDomain)
                .collect(Collectors.toList());
        stats.setNewRequests(newRequests);

        // 5. Aceitos hoje
        List<ServiceStatus> acceptedStatuses = Arrays.asList(
                ServiceStatus.VISIT_CONFIRMED, ServiceStatus.DATES_CONFIRMED, ServiceStatus.IN_PROGRESS
        );
        List<Service> acceptedToday = serviceRepository.findByCompanyIdAndStatusInAndDate(companyId, acceptedStatuses)
                .stream()
                .map(serviceMapper::toDomain)
                .collect(Collectors.toList());
        stats.setAcceptedToday(acceptedToday);


        // 6. Serviços pendentes (em negociação)
        List<ServiceStatus> pendingStatuses = Arrays.asList(
                ServiceStatus.NEGOTIATING_VISIT,
                ServiceStatus.NEGOTIATING_DATES,
                ServiceStatus.BUDGET_IN_NEGOTIATION,
                ServiceStatus.BUDGET_REVISION_REQUESTED
        );
        List<Service> pendingServices = serviceRepository.findByCompanyIdAndStatusIn(companyId, pendingStatuses)
                .stream()
                .map(serviceMapper::toDomain)
                .collect(Collectors.toList());
        stats.setPendingServices(pendingServices);

        return stats;
    }
}