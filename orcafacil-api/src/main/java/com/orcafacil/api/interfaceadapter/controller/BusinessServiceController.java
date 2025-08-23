package com.orcafacil.api.interfaceadapter.controller;

import com.orcafacil.api.application.service.service.BusinessServiceService;
import com.orcafacil.api.domain.service.Service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/services")
public class BusinessServiceController {

    private final BusinessServiceService serviceService;

    public BusinessServiceController(BusinessServiceService serviceService) {
        this.serviceService = serviceService;
    }

    // =============================
    // Criar serviço
    // =============================
    @PostMapping
    public ResponseEntity<Service> createService(@RequestBody Service service) {
        Service created = serviceService.create(service);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // =============================
    // Consultar serviço
    // =============================
    @GetMapping("/{id}")
    public ResponseEntity<Service> getService(@PathVariable Integer id) {
        return serviceService.findById(id)
                .map(s -> ResponseEntity.ok(s))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Service>> getAllServices() {
        return ResponseEntity.ok(serviceService.findAll());
    }

    // =============================
    // Aceitar solicitação pelo prestador
    // =============================
    @PostMapping("/{id}/accept")
    public ResponseEntity<Void> acceptService(@PathVariable Integer id) {
        serviceService.acceptServiceRequest(id);
        return ResponseEntity.ok().build();
    }

    // =============================
    // Confirmar visita técnica
    // =============================
    @PostMapping("/{id}/confirm-visit")
    public ResponseEntity<Void> confirmVisit(@PathVariable Integer id) {
        serviceService.confirmTechnicalVisit(id);
        return ResponseEntity.ok().build();
    }

    // =============================
    // Propor datas da obra
    // =============================
    @PostMapping("/{id}/propose-dates")
    public ResponseEntity<Void> proposeDates(@PathVariable Integer id,
                                             @RequestParam Date start,
                                             @RequestParam Date end) {
        serviceService.proposeWorkDates(id, start, end);
        return ResponseEntity.ok().build();
    }

    // =============================
    // Confirmar datas da obra
    // =============================
    @PostMapping("/{id}/confirm-dates")
    public ResponseEntity<Void> confirmDates(@PathVariable Integer id) {
        serviceService.confirmWorkDates(id);
        return ResponseEntity.ok().build();
    }

    // =============================
    // Enviar lista de materiais
    // =============================
    @PostMapping("/{id}/materials")
    public ResponseEntity<Void> sendMaterials(@PathVariable Integer id,
                                              @RequestBody List<String> materials) {
        serviceService.sendMaterialList(id, materials);
        return ResponseEntity.ok().build();
    }

    // =============================
    // Confirmar materiais
    // =============================
    @PostMapping("/{id}/confirm-materials")
    public ResponseEntity<Void> confirmMaterials(@PathVariable Integer id) {
        serviceService.confirmMaterialList(id);
        return ResponseEntity.ok().build();
    }

    // =============================
    // Solicitar nova lista de materiais
    // =============================
    @PostMapping("/{id}/request-new-materials")
    public ResponseEntity<Void> requestNewMaterials(@PathVariable Integer id) {
        serviceService.requestNewMaterialList(id);
        return ResponseEntity.ok().build();
    }

    // =============================
    // Finalizar serviço com avaliação
    // =============================
    @PostMapping("/{id}/finalize")
    public ResponseEntity<Void> finalizeService(@PathVariable Integer id,
                                                @RequestParam int rating) {
        serviceService.finalizeService(id, rating);
        return ResponseEntity.ok().build();
    }

    // =============================
    // Buscar serviços por usuário (ordenados)
    // =============================
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Service>> getServicesByUser(@PathVariable Integer userId) {
        return ResponseEntity.ok(serviceService.findByUserId(userId));
    }

    @GetMapping("/search/description/{description}")
    public List<Service> searchByDescription(@PathVariable String description) {
        return serviceService.getServicesByDescription(description);
    }
}
