package com.orcafacil.api.application.service.service;

import com.orcafacil.api.domain.service.Service;
import com.orcafacil.api.domain.service.ServiceRepository;
import com.orcafacil.api.domain.service.ServiceStatus;
import com.orcafacil.api.domain.user.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.Date;
import java.math.BigDecimal;
import java.util.Comparator;

@org.springframework.stereotype.Service
public class BusinessServiceService {

    private final ServiceRepository repository;
    private final UserRepository userRepository;

    public BusinessServiceService(ServiceRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    // =============================
    // CRUD Básico
    // =============================
    public Service create(Service service) {
        validateNewService(service);
        Service newService = service.withServiceStatus(ServiceStatus.CREATED);
        return repository.save(newService);
    }

    public Optional<Service> findById(Integer id) {
        return repository.findById(id);
    }

    public List<Service> findAll() {
        return repository.findAll();
    }

    private void validateNewService(Service service) {
        if (service.getUser() == null || userRepository.findById(service.getUser().getId()).isEmpty()) {
            throw new IllegalArgumentException("Cliente inválido.");
        }
    }

    public void acceptServiceRequest(Integer serviceId) {
        Service service = getServiceOrThrow(serviceId);
        Service updated = service.withServiceStatus(ServiceStatus.PENDING_VISIT);
        repository.save(updated);
    }

    public void confirmTechnicalVisit(Integer serviceId) {
        Service service = getServiceOrThrow(serviceId);
        Service updated = service.withVisitConfirmed(true)
                .withServiceStatus(ServiceStatus.VISIT_CONFIRMED);
        repository.save(updated);
    }

    public void proposeWorkDates(Integer serviceId, Date start, Date end) {
        Service service = getServiceOrThrow(serviceId);
        Service updated = service.withNegotiatedStartDate(start)
                .withNegotiatedEndDate(end)
                .withServiceStatus(ServiceStatus.WAITING_DATE_CONFIRMATION);
        repository.save(updated);
    }

    public void confirmWorkDates(Integer serviceId) {
        Service service = getServiceOrThrow(serviceId);
        Service updated = service.withServiceStatus(ServiceStatus.DATES_CONFIRMED);
        repository.save(updated);
    }

    public void setLaborCost(Integer serviceId, BigDecimal laborCost) {
        Service service = getServiceOrThrow(serviceId);
        Service updated = service.withLaborCost(laborCost);
        repository.save(updated);
    }

    public void finalizeService(Integer serviceId) {
        Service service = getServiceOrThrow(serviceId);
        Service updated = service.withServiceStatus(ServiceStatus.FINISHED)
                .withBudgetFinalized(true);
        repository.save(updated);
    }

    public void sendMaterialList(Integer serviceId, List<String> materials) {
        Service service = getServiceOrThrow(serviceId);
        Service updated = service.withServiceStatus(ServiceStatus.WAITING_MATERIAL_CONFIRMATION);
        repository.save(updated);
    }

    public void confirmMaterialList(Integer serviceId) {
        Service service = getServiceOrThrow(serviceId);
        Service updated = service.withServiceStatus(ServiceStatus.MATERIALS_CONFIRMED);
        repository.save(updated);
    }

    public void requestNewMaterialList(Integer serviceId) {
        Service service = getServiceOrThrow(serviceId);
        Service updated = service.withServiceStatus(ServiceStatus.WAITING_NEW_MATERIAL_LIST);
        repository.save(updated);
    }

    public void finalizeService(Integer serviceId, int rating) {
        Service service = getServiceOrThrow(serviceId);
        Service updated = service.withServiceStatus(ServiceStatus.FINISHED)
                .withBudgetFinalized(true);
        repository.save(updated);
    }

    // =============================
    // Buscar serviços por usuário
    // =============================
    public List<Service> findByUserId(Integer userId) {
        List<Service> services = repository.findByUserId(userId);

        return services.stream()
                .sorted(Comparator.comparingInt(s -> {
                    if (s.getServiceStatus() == ServiceStatus.CREATED ||
                            s.getServiceStatus() == ServiceStatus.PENDING_VISIT ||
                            s.getServiceStatus() == ServiceStatus.WAITING_DATE_CONFIRMATION ||
                            s.getServiceStatus() == ServiceStatus.WAITING_MATERIAL_CONFIRMATION) {
                        return 0; // pendentes vêm primeiro
                    } else if (s.getServiceStatus() == ServiceStatus.VISIT_CONFIRMED ||
                            s.getServiceStatus() == ServiceStatus.DATES_CONFIRMED ||
                            s.getServiceStatus() == ServiceStatus.MATERIALS_CONFIRMED) {
                        return 1; // intermediários no meio
                    } else if (s.getServiceStatus() == ServiceStatus.FINISHED) {
                        return 2; // finalizados depois
                    } else {
                        return 3; // qualquer outro status vai pro fim
                    }
                }))
                .toList();
    }

    // =============================
    // Utilitário
    // =============================
    private Service getServiceOrThrow(Integer serviceId) {
        return repository.findById(serviceId)
                .orElseThrow(() -> new IllegalArgumentException("Serviço não encontrado."));
    }

    public List<Service> getServicesByDescription(String description) {
        return repository.findByDescription(description);
    }
}


