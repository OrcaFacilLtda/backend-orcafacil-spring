package com.orcafacil.api.application.service.service;

import com.orcafacil.api.application.service.provider.ProviderService;
import com.orcafacil.api.domain.budgetrevisionrequest.BudgetRevisionRequest;
import com.orcafacil.api.domain.budgetrevisionrequest.BudgetRevisionRequestRepository;
import com.orcafacil.api.domain.company.CompanyRepository;
import com.orcafacil.api.domain.evaluation.Evaluation;
import com.orcafacil.api.domain.evaluation.EvaluationRepository;
import com.orcafacil.api.domain.provider.Provider;
import com.orcafacil.api.domain.service.Service;
import com.orcafacil.api.domain.service.ServiceRepository;
import com.orcafacil.api.domain.service.ServiceStatus;
import com.orcafacil.api.domain.user.User;
import com.orcafacil.api.domain.user.UserRepository;
import com.orcafacil.api.domain.user.UserType;
import com.orcafacil.api.interfaceadapter.request.sevice.ServiceRequest;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class BusinessServiceService {
    private final ServiceRepository serviceRepository;
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final BudgetRevisionRequestRepository revisionRequestRepository;
    private final EvaluationRepository evaluationRepository;
    private final ProviderService providerService;

    public BusinessServiceService(
            ServiceRepository serviceRepository,
            UserRepository userRepository,
            CompanyRepository companyRepository,
            BudgetRevisionRequestRepository revisionRequestRepository,
            EvaluationRepository evaluationRepository,
            ProviderService providerService) {
        this.serviceRepository = serviceRepository;
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
        this.revisionRequestRepository = revisionRequestRepository;
        this.evaluationRepository = evaluationRepository;
        this.providerService = providerService;
    }

    /**
     * MÉTODO CORRIGIDO E FINAL
     * Usa o construtor completo da entidade Service com valores iniciais corretos.
     */
    @Transactional
    public Service create(ServiceRequest dto) {
        // 1. Busca as entidades User e Company a partir dos IDs do DTO.
        var user = userRepository.findById(dto.user().id().intValue())
                .orElseThrow(() -> new EntityNotFoundException("Usuário com ID " + dto.user().id() + " não encontrado."));

        var company = companyRepository.findById(dto.company().id().intValue())
                .orElseThrow(() -> new EntityNotFoundException("Empresa com ID " + dto.company().id() + " não encontrada."));

        Service newService = new Service(
                null, // id (gerado pelo banco)
                user,
                company,
                dto.description(),
                ServiceStatus.REQUEST_SENT,
                Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()),
                null,
                false,
                false,
                false,
                false,
                false,
                false,
                null,
                null,
                null,
                false
        );

        return serviceRepository.save(newService);
    }


    public long countServicesByCompanyId(Integer companyId) {
        return serviceRepository.countByCompanyId(companyId);
    }

    public long countServicesByCompanyIdAndStatusNotIn(Integer companyId, List<ServiceStatus> excludedStatuses) {
        return serviceRepository.countByCompanyIdAndStatusNotIn(companyId, excludedStatuses);
    }

    public Double getAverageRatingByCompanyId(Integer companyId) {
        return serviceRepository.findAverageRatingByCompanyId(companyId);
    }

    public List<Service> findServicesByCompanyIdAndStatus(Integer companyId, ServiceStatus status) {
        return serviceRepository.findByCompanyIdAndStatus(companyId, status);
    }

    public List<Service> findServicesByCompanyIdAndStatusIn(Integer companyId, List<ServiceStatus> statuses) {
        return serviceRepository.findByCompanyIdAndStatusIn(companyId, statuses);
    }

    public List<Service> findServicesAcceptedTodayByCompanyId(Integer companyId, List<ServiceStatus> statuses) {
        return serviceRepository.findAcceptedTodayByCompanyId(companyId, statuses);
    }

    public Provider findProviderByCompanyId(Integer companyId) {
        return providerService.findByCompanyId(companyId)
                .orElse(null);
    }

    private Service getServiceOrThrow(Integer serviceId) {
        return serviceRepository.findById(serviceId)
                .orElseThrow(() -> new IllegalArgumentException("Serviço não encontrado."));
    }

    private User getUserOrThrow(Integer userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));
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
    public List<Service> findByComapanyId(Integer companyId) {
        return serviceRepository.findByCompanyId(companyId);
    }


    @Transactional
    public Service acceptService(Integer serviceId, Integer providerId) {
        Service service = getServiceOrThrow(serviceId);
        if (!service.getCompany().getId().equals(providerId)) {
            throw new IllegalStateException("Apenas o prestador de serviço responsável pode aceitar a solicitação.");
        }
        Service updatedService = service.withServiceStatus(ServiceStatus.NEGOTIATING_VISIT);
        return serviceRepository.save(updatedService);
    }

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
            updatedService = updatedService.withServiceStatus(ServiceStatus.IN_PROGRESS);
        }

        return serviceRepository.save(updatedService);
    }
}