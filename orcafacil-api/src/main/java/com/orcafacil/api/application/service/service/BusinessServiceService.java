package com.orcafacil.api.application.service.service;

import com.orcafacil.api.domain.budgetrevisionrequest.BudgetRevisionRequest;
import com.orcafacil.api.domain.budgetrevisionrequest.BudgetRevisionRequestRepository;
import com.orcafacil.api.domain.evaluation.Evaluation;
import com.orcafacil.api.domain.evaluation.EvaluationRepository;
import com.orcafacil.api.domain.service.Service;
import com.orcafacil.api.domain.service.ServiceRepository;
import com.orcafacil.api.domain.service.ServiceStatus;
import com.orcafacil.api.domain.user.User;
import com.orcafacil.api.domain.user.UserRepository;
import com.orcafacil.api.domain.user.UserType;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service // <-- Alterado de @Component para @Service para maior clareza semântica
public class BusinessServiceService {

    private final ServiceRepository serviceRepository;
    private final UserRepository userRepository;
    private final BudgetRevisionRequestRepository revisionRequestRepository;
    private final EvaluationRepository evaluationRepository;

    // Construtor atualizado com todas as dependências
    public BusinessServiceService(
            ServiceRepository serviceRepository,
            UserRepository userRepository,
            BudgetRevisionRequestRepository revisionRequestRepository,
            EvaluationRepository evaluationRepository) {
        this.serviceRepository = serviceRepository;
        this.userRepository = userRepository;
        this.revisionRequestRepository = revisionRequestRepository;
        this.evaluationRepository = evaluationRepository;
    }

    private Service getServiceOrThrow(Integer serviceId) {
        return serviceRepository.findById(serviceId)
                .orElseThrow(() -> new IllegalArgumentException("Serviço não encontrado."));
    }

    private User getUserOrThrow(Integer userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));
    }

    // --- MÉTODOS CRUD E DE BUSCA ---

    @Transactional
    public Service create(Service service) {
        Service newService = service.withServiceStatus(ServiceStatus.REQUEST_SENT);
        return serviceRepository.save(newService);
    }

    public Optional<Service> findById(Integer id) {
        return serviceRepository.findById(id);
    }

    public List<Service> findAll() {
        return serviceRepository.findAll();
    }

    public List<Service> findByUserId(Integer userId) {
        return serviceRepository.findByUserId(userId);
    }

    // --- LÓGICA DE NEGÓCIO DO FLUXO DE SERVIÇO ---

    @Transactional
    public Service rejectService(Integer serviceId, Integer providerId) {
        Service service = getServiceOrThrow(serviceId);
        if (!service.getCompany().getId().equals(providerId)) {
            throw new IllegalStateException("Apenas o prestador de serviço responsável pode recusar a solicitação.");
        }
        Service updatedService = service.withServiceStatus(ServiceStatus.REJECTED);
        return serviceRepository.save(updatedService);
    }

    @Transactional
    public Service requestBudgetRevision(Integer serviceId, Integer clientId) {
        Service service = getServiceOrThrow(serviceId);
        User client = getUserOrThrow(clientId);

        if (!service.getUser().getId().equals(clientId)) {
            throw new IllegalStateException("Apenas o cliente responsável pode solicitar a revisão.");
        }

        BudgetRevisionRequest revisionRequest = new BudgetRevisionRequest(null, service, client, LocalDateTime.now());
        revisionRequestRepository.save(revisionRequest);

        Service updatedService = service.withServiceStatus(ServiceStatus.BUDGET_REVISION_REQUESTED);
        return serviceRepository.save(updatedService);
    }

    @Transactional
    public Service finalizeAndEvaluate(Integer serviceId, Integer clientId, int stars) {
        Service service = getServiceOrThrow(serviceId);
        User client = getUserOrThrow(clientId);

        if (!service.getUser().getId().equals(clientId)) {
            throw new IllegalStateException("Apenas o cliente do serviço pode finalizá-lo e avaliá-lo.");
        }

        Evaluation evaluation = new Evaluation(null, service, stars, LocalDateTime.now());
        evaluationRepository.save(evaluation);

        Service updatedService = service.withServiceStatus(ServiceStatus.COMPLETED);
        return serviceRepository.save(updatedService);
    }

    // --- MÉTODOS DE CONFIRMAÇÃO BILATERAL ---

    @Transactional
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

        if (updatedService.getClientVisitConfirmed() && updatedService.getProviderVisitConfirmed()) {
            updatedService = updatedService.withServiceStatus(ServiceStatus.VISIT_CONFIRMED);
        }

        return serviceRepository.save(updatedService);
    }

    @Transactional
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

        return serviceRepository.save(updatedService);
    }

    @Transactional
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
            updatedService = updatedService.withServiceStatus(ServiceStatus.IN_PROGRESS); // Orçamento aprovado, serviço em execução
        }

        return serviceRepository.save(updatedService);
    }
}