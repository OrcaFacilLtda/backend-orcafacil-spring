package com.orcafacil.api.interfaceadapter.controller;

import com.orcafacil.api.application.service.provider.ProviderService;
import com.orcafacil.api.domain.provider.Provider;
import com.orcafacil.api.interfaceadapter.request.provider.CreateProviderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/providers")
@RequiredArgsConstructor
public class ProviderController {
    private final ProviderService service;

    @PostMapping
    public ResponseEntity<Provider> create(@RequestBody CreateProviderRequest request) {
        return ResponseEntity.ok(service.create(request.getUser(), request.getCompany(),request.getCategory()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Provider> findById(@PathVariable Integer id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/company/{companyId}")
    public ResponseEntity<Provider> findByCompanyId(@PathVariable Integer companyId) {
        return service.findByCompanyId(companyId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}