package com.orcafacil.api.interfaceadapter.controller;

import com.orcafacil.api.application.service.service.BusinessServiceService;
import com.orcafacil.api.domain.datenegotiation.DateNegotiation;
import com.orcafacil.api.domain.materiallist.MaterialList;
import com.orcafacil.api.domain.service.Service;
import com.orcafacil.api.domain.visitnegotiation.VisitNegotiation;
import com.orcafacil.api.interfaceadapter.request.sevice.DateRangeProposalRequest;
import com.orcafacil.api.interfaceadapter.request.sevice.*;
import com.orcafacil.api.interfaceadapter.response.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/services")
public class BusinessServiceController {

    private final BusinessServiceService service;

    public BusinessServiceController(BusinessServiceService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Service>> create(@RequestBody @Valid ServiceRequest serviceRequest) {
        Service created = service.create(serviceRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Serviço criado com sucesso.", created));
    }

    @PostMapping("/{serviceId}/provider/{providerId}/accept")
    public ResponseEntity<ApiResponse<Service>> acceptService(
            @PathVariable Integer serviceId,
            @PathVariable Integer providerId) {
        Service updatedService = service.acceptService(serviceId, providerId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Serviço aceito com sucesso.", updatedService));
    }

    @PostMapping("/{serviceId}/provider/{providerId}/reject")
    public ResponseEntity<ApiResponse<Service>> rejectService(
            @PathVariable Integer serviceId,
            @PathVariable Integer providerId) {
        Service updatedService = service.rejectService(serviceId, providerId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Serviço recusado com sucesso.", updatedService));
    }


    @PostMapping("/{serviceId}/confirm-visit/{userId}")
    public ResponseEntity<ApiResponse<Service>> confirmVisit(
            @PathVariable Integer serviceId,
            @PathVariable Integer userId) {
        Service updated = service.confirmVisit(serviceId, userId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Confirmação de visita registrada.", updated));
    }

    @PostMapping("/{serviceId}/confirm-dates/{userId}")
    public ResponseEntity<ApiResponse<Service>> confirmDates(
            @PathVariable Integer serviceId,
            @PathVariable Integer userId) {
        Service updated = service.confirmWorkDates(serviceId, userId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Confirmação de datas registrada.", updated));
    }

    @PostMapping("/{serviceId}/confirm-materials/{userId}")
    public ResponseEntity<ApiResponse<Service>> confirmMaterials(
            @PathVariable Integer serviceId,
            @PathVariable Integer userId) {
        Service updated = service.confirmMaterials(serviceId, userId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Confirmação de materiais registrada.", updated));
    }


    @PostMapping("/{serviceId}/propose-visit")
    public ResponseEntity<ApiResponse<VisitNegotiation>> sendVisitProposal(
            @PathVariable Integer serviceId, @RequestBody @Valid VisitProposalRequest request) {
        VisitNegotiation visitNegotiation = service.sendVisitProposal(serviceId, request);
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Proposta de visita enviada com sucesso.", visitNegotiation));
    }

    @PostMapping("/{serviceId}/propose-dates")
    public ResponseEntity<ApiResponse<DateNegotiation>> sendDateProposal(
            @PathVariable Integer serviceId, @RequestBody @Valid DateRangeProposalRequest request) {
        DateNegotiation dateNegotiation = service.sendDateProposal(serviceId, request);
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Proposta de datas enviada com sucesso.", dateNegotiation));
    }

    @PostMapping("/{serviceId}/materials")
    public ResponseEntity<ApiResponse<Service>> sendMaterialList(
            @PathVariable Integer serviceId, @RequestBody @Valid MaterialRequest request) {
        Service updatedService = service.sendMaterialList(serviceId, request.materials());
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Lista de materiais enviada com sucesso.", updatedService));
    }

    @PostMapping("/{serviceId}/request-revision")
    public ResponseEntity<ApiResponse<Service>> requestBudgetRevision(
            @PathVariable Integer serviceId, @RequestParam Integer clientId) {
        Service updatedService = service.requestBudgetRevision(serviceId, clientId);
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Solicitação de revisão de orçamento enviada.", updatedService));
    }

    @PostMapping("/{serviceId}/evaluate")
    public ResponseEntity<ApiResponse<Service>> submitEvaluation(
            @PathVariable Integer serviceId,
            @RequestParam Integer clientId,
            @RequestBody @Valid EvaluationRequest request) {
        Service updatedService = service.finalizeAndEvaluate(serviceId, clientId, request.getStars());
        return ResponseEntity.ok(new ApiResponse<>(true, "Avaliação enviada com sucesso.", updatedService));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Service>> findById(@PathVariable Integer id) {
        return service.findById(id)
                .map(s -> ResponseEntity.ok(new ApiResponse<>(true, "Serviço encontrado.", s)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false, "Serviço não encontrado.", null)));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<Service>>> findByUserId(@PathVariable Integer userId) {
        List<Service> services = service.findByUserId(userId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Serviços do usuário listados.", services));
    }

    @GetMapping("/company/{companyId}")
    public ResponseEntity<ApiResponse<List<Service>>> findByCompanyId(@PathVariable Integer companyId) {
        List<Service> services = service.findByComapanyId(companyId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Serviços do prestador listados.", services));
    }



    @GetMapping("/{serviceId}/materials")
    public ResponseEntity<ApiResponse<List<MaterialList>>> getMaterialsByServiceId(@PathVariable Integer serviceId) {
        List<MaterialList> materials = service.findMaterialsByServiceId(serviceId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Lista de materiais recuperada.", materials));
    }

    @GetMapping("/{serviceId}/visit-proposals")
    public ResponseEntity<ApiResponse<List<VisitNegotiation>>> getVisitProposals(@PathVariable Integer serviceId) {
        List<VisitNegotiation> proposals = service.findVisitProposalsByServiceId(serviceId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Propostas de visita recuperadas.", proposals));
    }

    @GetMapping("/{serviceId}/date-proposals")
    public ResponseEntity<ApiResponse<List<DateNegotiation>>> getDateProposals(@PathVariable Integer serviceId) {
        List<DateNegotiation> proposals = service.findDateProposalsByServiceId(serviceId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Propostas de data recuperadas.", proposals));
    }
}