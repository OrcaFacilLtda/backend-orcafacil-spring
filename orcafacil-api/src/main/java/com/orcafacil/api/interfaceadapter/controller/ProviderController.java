package com.orcafacil.api.interfaceadapter.controller;

import com.orcafacil.api.application.service.provider.ProviderService;
import com.orcafacil.api.domain.provider.Provider;
import com.orcafacil.api.interfaceadapter.request.provider.CreateProviderRequest;
import com.orcafacil.api.interfaceadapter.request.provider.UpdateProviderRequest;
import com.orcafacil.api.interfaceadapter.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/providers")
public class ProviderController {

    private final ProviderService service;

    public ProviderController(ProviderService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Provider>> create(@RequestBody CreateProviderRequest request) {
        Provider provider = service.create(request);
        return ResponseEntity.status(201)
                .body(new ApiResponse<>(true, "Fornecedor criado com sucesso.", provider));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Provider>> findById(@PathVariable Integer id) {
        return service.findById(id)
                .map(provider -> ResponseEntity.ok(new ApiResponse<>(true, "Fornecedor encontrado.", provider)))
                .orElse(ResponseEntity.status(404)
                        .body(new ApiResponse<>(false, "Fornecedor não encontrado.", null)));
    }

    @GetMapping("/company/{companyId}")
    public ResponseEntity<ApiResponse<Provider>> findByCompanyId(@PathVariable Integer companyId) {
        return service.findByCompanyId(companyId)
                .map(provider -> ResponseEntity.ok(new ApiResponse<>(true, "Fornecedor encontrado por empresa.", provider)))
                .orElse(ResponseEntity.status(404)
                        .body(new ApiResponse<>(false, "Fornecedor não encontrado por empresa.", null)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Provider>> update(
            @PathVariable Integer id,
            @RequestBody UpdateProviderRequest request
    ) {
        Provider updated = service.update(id, request);
        return ResponseEntity.ok(new ApiResponse<>(true, "Fornecedor atualizado com sucesso.", updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteById(@PathVariable Integer id) {
        service.deleteById(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Fornecedor deletado com sucesso.", null));
    }
}
