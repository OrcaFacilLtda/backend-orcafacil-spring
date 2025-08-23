package com.orcafacil.api.application.service.service;

import com.orcafacil.api.domain.service.Service;
import com.orcafacil.api.domain.service.ServiceRepository;
import com.orcafacil.api.domain.service.ServiceStatus;
import com.orcafacil.api.domain.user.User;
import com.orcafacil.api.domain.user.UserRepository;
import com.orcafacil.api.domain.user.UserType;

import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

@Component
public class BusinessServiceService {

    private final ServiceRepository repository;
    private final UserRepository userRepository;

    public BusinessServiceService(ServiceRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    private Service getServiceOrThrow(Integer serviceId) {
        return repository.findById(serviceId)
                .orElseThrow(() -> new IllegalArgumentException("Serviço não encontrado."));
    }

    private User getUserOrThrow(Integer userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));
    }

    public Service create(Service service) {
        // ... sua lógica de criação
        return repository.save(service);
    }

    public Optional<Service> findById(Integer id) {
        return repository.findById(id);
    }

    public List<Service> findAll() {
        return repository.findAll();
    }

    public List<Service> findByUserId(Integer userId) {
        return repository.findByUserId(userId);
    }

    // --- LÓGICA DE CONFIRMAÇÃO AJUSTADA ---

    /**
     * Confirma a visita técnica por parte de um usuário (cliente ou prestador).
     * Se ambos confirmarem, o status do serviço avança para VISIT_CONFIRMED.
     */
    public Service confirmVisit(Integer serviceId, Integer userId) {
        Service service = getServiceOrThrow(serviceId);
        User user = getUserOrThrow(userId);

        Service updatedService;
        if (user.getUserType() == UserType.CLIENT) {
            updatedService = service.withClientVisitConfirmed(true);
        } else if (user.getUserType() == UserType.PROVIDER) {
            updatedService = service.withProviderVisitConfirmed(true);
        } else {
            throw new IllegalStateException("Apenas clientes ou prestadores podem confirmar a visita.");
        }

        // Se ambos confirmaram, avança o status
        if (updatedService.getClientVisitConfirmed() && updatedService.getProviderVisitConfirmed()) {
            updatedService = updatedService.withServiceStatus(ServiceStatus.VISIT_CONFIRMED);
        }

        return repository.save(updatedService);
    }

    /**
     * Confirma as datas da obra por parte de um usuário.
     * Se ambos confirmarem, o status avança para DATES_CONFIRMED.
     */
    public Service confirmWorkDates(Integer serviceId, Integer userId) {
        Service service = getServiceOrThrow(serviceId);
        User user = getUserOrThrow(userId);

        Service updatedService;
        if (user.getUserType() == UserType.CLIENT) {
            updatedService = service.withClientDatesConfirmed(true);
        } else if (user.getUserType() == UserType.PROVIDER) {
            updatedService = service.withProviderDatesConfirmed(true);
        } else {
            throw new IllegalStateException("Apenas clientes ou prestadores podem confirmar as datas.");
        }

        if (updatedService.getClientDatesConfirmed() && updatedService.getProviderDatesConfirmed()) {
            updatedService = updatedService.withServiceStatus(ServiceStatus.DATES_CONFIRMED);
        }

        return repository.save(updatedService);
    }

    /**
     * Confirma a lista de materiais e o orçamento.
     * Se ambos confirmarem, o status avança para MATERIALS_CONFIRMED.
     */
    public Service confirmMaterials(Integer serviceId, Integer userId) {
        Service service = getServiceOrThrow(serviceId);
        User user = getUserOrThrow(userId);

        Service updatedService;
        if (user.getUserType() == UserType.CLIENT) {
            updatedService = service.withClientMaterialsConfirmed(true);
        } else if (user.getUserType() == UserType.PROVIDER) {
            updatedService = service.withProviderMaterialsConfirmed(true);
        } else {
            throw new IllegalStateException("Apenas clientes ou prestadores podem confirmar os materiais.");
        }

        if (updatedService.getClientMaterialsConfirmed() && updatedService.getProviderMaterialsConfirmed()) {
            updatedService = updatedService.withServiceStatus(ServiceStatus.MATERIALS_CONFIRMED);
        }

        return repository.save(updatedService);
    }
}