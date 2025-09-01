package com.orcafacil.api.application.service.service;

import com.orcafacil.api.application.service.provider.ProviderService;
import com.orcafacil.api.domain.budgetrevisionrequest.BudgetRevisionRequest;
import com.orcafacil.api.domain.budgetrevisionrequest.BudgetRevisionRequestRepository;
import com.orcafacil.api.domain.company.CompanyRepository;
import com.orcafacil.api.domain.datenegotiation.DateNegotiation;
import com.orcafacil.api.domain.datenegotiation.DateNegotiationRepository;
import com.orcafacil.api.domain.evaluation.Evaluation;
import com.orcafacil.api.domain.evaluation.EvaluationRepository;
import com.orcafacil.api.domain.materiallist.MaterialList;
import com.orcafacil.api.domain.materiallist.MaterialListRepository;
import com.orcafacil.api.domain.provider.Provider;
import com.orcafacil.api.domain.service.Service;
import com.orcafacil.api.domain.service.ServiceRepository;
import com.orcafacil.api.domain.service.ServiceStatus;
import com.orcafacil.api.domain.user.User;
import com.orcafacil.api.domain.user.UserRepository;
import com.orcafacil.api.domain.user.UserType;
import com.orcafacil.api.domain.visitnegotiation.VisitNegotiation;
import com.orcafacil.api.domain.visitnegotiation.VisitNegotiationRepository;
import com.orcafacil.api.interfaceadapter.request.sevice.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
public class BusinessServiceService {
    private final ServiceRepository serviceRepository;
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final BudgetRevisionRequestRepository revisionRequestRepository;
    private final EvaluationRepository evaluationRepository;
    private final ProviderService providerService;
    private final VisitNegotiationRepository visitNegotiationRepository;
    private final DateNegotiationRepository dateNegotiationRepository;
    private final MaterialListRepository materialListRepository;

    public BusinessServiceService(
            ServiceRepository serviceRepository,
            UserRepository userRepository,
            CompanyRepository companyRepository,
            BudgetRevisionRequestRepository revisionRequestRepository,
            EvaluationRepository evaluationRepository,
            ProviderService providerService,
            VisitNegotiationRepository visitNegotiationRepository,
            DateNegotiationRepository dateNegotiationRepository,
            MaterialListRepository materialListRepository) {
        this.serviceRepository = serviceRepository;
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
        this.revisionRequestRepository = revisionRequestRepository;
        this.evaluationRepository = evaluationRepository;
        this.providerService = providerService;
        this.visitNegotiationRepository = visitNegotiationRepository;
        this.dateNegotiationRepository = dateNegotiationRepository;
        this.materialListRepository = materialListRepository;
    }

    @Transactional
    public Service create(ServiceRequest dto) {
        var user = userRepository.findById(dto.user().id().intValue())
                .orElseThrow(() -> new EntityNotFoundException("Usuário com ID " + dto.user().id() + " não encontrado."));

        var company = companyRepository.findById(dto.company().id().intValue())
                .orElseThrow(() -> new EntityNotFoundException("Empresa com ID " + dto.company().id() + " não encontrada."));

        Service newService = new Service(
                null, user, company, dto.description(), ServiceStatus.REQUEST_SENT,
                Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()),
                null, false, false, false, false, false, false, null, null, null, false);

        return serviceRepository.save(newService);
    }


    public Optional<Service> findById(Integer id) { return serviceRepository.findById(id); }
    public List<Service> findAll() { return serviceRepository.findAll(); }
    public List<Service> findByUserId(Integer userId) { return serviceRepository.findByUserId(userId); }
    public List<Service> findByComapanyId(Integer companyId) { return serviceRepository.findByCompanyId(companyId); }
    public Provider findProviderByCompanyId(Integer companyId) { return providerService.findByCompanyId(companyId).orElse(null); }

    public List<VisitNegotiation> findVisitProposalsByServiceId(Integer serviceId) {
        return visitNegotiationRepository.findByServiceId(serviceId);
    }

    public List<DateNegotiation> findDateProposalsByServiceId(Integer serviceId) {
        return dateNegotiationRepository.findByServiceId(serviceId);
    }

    public List<MaterialList> findMaterialsByServiceId(Integer serviceId) {
        return materialListRepository.findByServiceId(serviceId);
    }

    public long countServicesByCompanyId(Integer companyId) { return serviceRepository.countByCompanyId(companyId); }
    public long countServicesByCompanyIdAndStatusNotIn(Integer companyId, List<ServiceStatus> excludedStatuses) { return serviceRepository.countByCompanyIdAndStatusNotIn(companyId, excludedStatuses); }
    public Double getAverageRatingByCompanyId(Integer companyId) { return serviceRepository.findAverageRatingByCompanyId(companyId); }
    public List<Service> findServicesByCompanyIdAndStatus(Integer companyId, ServiceStatus status) { return serviceRepository.findByCompanyIdAndStatus(companyId, status); }
    public List<Service> findServicesByCompanyIdAndStatusIn(Integer companyId, List<ServiceStatus> statuses) { return serviceRepository.findByCompanyIdAndStatusIn(companyId, statuses); }
    public List<Service> findServicesAcceptedTodayByCompanyId(Integer companyId, List<ServiceStatus> statuses) { return serviceRepository.findAcceptedTodayByCompanyId(companyId, statuses); }


    private Service getServiceOrThrow(Integer serviceId) {
        return serviceRepository.findById(serviceId)
                .orElseThrow(() -> new IllegalArgumentException("Serviço não encontrado."));
    }

    private User getUserOrThrow(Integer userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));
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

    @Transactional
    public VisitNegotiation sendVisitProposal(Integer serviceId, VisitProposalRequest request) {
        Service service = getServiceOrThrow(serviceId);

        if (service.getServiceStatus() != ServiceStatus.NEGOTIATING_VISIT) {
            throw new IllegalStateException("A negociação/visita só pode iniciar após o prestador aceitar o serviço.");
        }

        VisitNegotiation visitNegotiation = new VisitNegotiation(
                null,
                service,
                request.proposerRole(),
                request.date(),
                LocalDateTime.now(),
                false
        );
        return visitNegotiationRepository.save(visitNegotiation);
    }

    @Transactional
    public Service sendMaterialList(Integer serviceId, List<MaterialRequest.MaterialItem> materials) {
        Service service = getServiceOrThrow(serviceId);

        List<MaterialList> materialLists = materials.stream()
                .map(item -> new MaterialList(null, service, item.name(), item.quantity(), item.unitPrice()))
                .collect(Collectors.toList());
        materialListRepository.saveAll(materialLists);

        Service updatedService = service.withProviderMaterialsConfirmed(true)
                .withServiceStatus(ServiceStatus.BUDGET_IN_NEGOTIATION); // Atualiza o status

        return serviceRepository.save(updatedService);
    }

    // ✅ MÉTODO ATUALIZADO AQUI
    @Transactional
    public Service requestBudgetRevision(Integer serviceId, Integer clientId) {
        Service service = getServiceOrThrow(serviceId);
        User client = getUserOrThrow(clientId);

        if (!service.getUser().getId().equals(clientId)) {
            throw new IllegalStateException("Apenas o cliente responsável pode solicitar a revisão.");
        }

        materialListRepository.deleteByServiceId(serviceId);

        BudgetRevisionRequest revisionRequest = new BudgetRevisionRequest(null, service, client, LocalDateTime.now());
        revisionRequestRepository.save(revisionRequest);

        Service updatedService = service.withServiceStatus(ServiceStatus.BUDGET_REVISION_REQUESTED);
        updatedService = updatedService.withClientMaterialsConfirmed(false).withProviderMaterialsConfirmed(false);

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

        Service updatedService = service.withServiceStatus(ServiceStatus.COMPLETED)
                .withBudgetFinalized(true);

        return serviceRepository.save(updatedService);
    }

    @Transactional
    public DateNegotiation sendDateProposal(Integer serviceId, DateRangeProposalRequest request) {
        Service service = getServiceOrThrow(serviceId);

        if (request.endDate().before(request.startDate())) {
            throw new IllegalArgumentException("A data de término não pode ser anterior à data de início.");
        }

        DateNegotiation dateNegotiation =
                new DateNegotiation(
                        null,
                        service,
                        request.proposerRole(),
                        request.startDate(),
                        request.endDate(),
                        LocalDateTime.now(),
                        false);
        return dateNegotiationRepository.save(dateNegotiation);
    }
}