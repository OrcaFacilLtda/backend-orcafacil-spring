package com.orcafacil.api.interfaceadapter.controller;

import com.orcafacil.api.application.service.service.BusinessServiceService;
import com.orcafacil.api.domain.service.Service;
import com.orcafacil.api.interfaceadapter.request.sevice.ServiceRequest;
import com.orcafacil.api.interfaceadapter.response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/services")
public class BusinessServiceController {

    private final BusinessServiceService service;

    public BusinessServiceController(BusinessServiceService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Service>> create(@RequestBody @Valid ServiceRequest serviceRequest) { // ALTERADO AQUI
        Service created = service.create(serviceRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Serviço criado com sucesso.", created));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Service>> findById(@PathVariable Integer id) {
        return service.findById(id)
                .map(s -> ResponseEntity.ok(new ApiResponse<>(true, "Serviço encontrado.", s)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false, "Serviço não encontrado.", null)));
    }

    @GetMapping("/service/user/{userId}")
    public ResponseEntity<ApiResponse<List<Service>>> findByUserId(@PathVariable Integer userId) {
        List<Service> services = service.findByUserId(userId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Serviços do usuário listados.", services));
    }

    @GetMapping("/service/company/{comapnyId}")
    public ResponseEntity<ApiResponse<List<Service>>> findByComapanyId(@PathVariable Integer comapnyId) {
        List<Service> services = service.findByComapanyId(comapnyId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Serviços do prestador listados.", services));
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


}