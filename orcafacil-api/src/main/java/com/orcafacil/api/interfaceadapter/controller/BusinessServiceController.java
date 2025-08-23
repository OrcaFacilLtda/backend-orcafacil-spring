package com.orcafacil.api.interfaceadapter.controller;

import com.orcafacil.api.application.service.service.BusinessServiceService;
import com.orcafacil.api.domain.service.Service;
import com.orcafacil.api.interfaceadapter.response.ApiResponse;
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
    public ResponseEntity<ApiResponse<Service>> create(@RequestBody Service serviceRequest) {
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

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<Service>>> findByUserId(@PathVariable Integer userId) {
        List<Service> services = service.findByUserId(userId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Serviços do usuário listados.", services));
    }

    // --- ENDPOINTS DE CONFIRMAÇÃO AJUSTADOS ---

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
}