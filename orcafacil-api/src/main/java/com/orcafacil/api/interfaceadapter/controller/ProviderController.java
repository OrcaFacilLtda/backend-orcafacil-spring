package com.orcafacil.api.interfaceadapter.controller;

import com.orcafacil.api.application.service.provider.ProviderService;
import com.orcafacil.api.domain.provider.Provider;
import com.orcafacil.api.interfaceadapter.request.provider.CreateProviderRequest;
import com.orcafacil.api.interfaceadapter.request.provider.UpdateProviderRequest;
import com.orcafacil.api.interfaceadapter.response.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Provider>> update(
            @PathVariable Integer id,
            @RequestBody UpdateProviderRequest request
    ) {
        Provider updated = service.update(id, request);
        return ResponseEntity.ok(new ApiResponse<>(true, "Fornecedor atualizado com sucesso.", updated));
    }

    private static final Logger logger = LoggerFactory.getLogger(ProviderController.class);

    @PutMapping("/admin/{providerId}")
    public ResponseEntity<ApiResponse<Provider>> updateProviderByAdmin(
            @PathVariable Integer providerId,
            @RequestBody UpdateProviderRequest updateRequest) {

        try {
            Provider updatedProvider = service.updateByAdmin(providerId, updateRequest);
            return ResponseEntity.ok(new ApiResponse<>(true, "Fornecedor atualizado pelo admin com sucesso.", updatedProvider));

        } catch (IllegalArgumentException ex) {
            // Loga o erro como um aviso (WARN) pois é um erro de dados do cliente (400)
            logger.warn("Requisição inválida para atualizar provider ID {}: {}", providerId, ex.getMessage());
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, ex.getMessage(), null));

        } catch (DataIntegrityViolationException ex) {
            // Sugestão: Captura erros de violação de constraint (ex: CNPJ duplicado)
            logger.error("Violação de dados ao tentar atualizar provider ID {}", providerId, ex);
            return ResponseEntity.status(409) // 409 Conflict é mais apropriado
                    .body(new ApiResponse<>(false, "Erro de dados: já existe um registro com as informações fornecidas.", null));

        } catch (Exception ex) {
            // 2. Loga o erro inesperado (500) com detalhes completos
            logger.error("Erro inesperado ao atualizar provider ID {}", providerId, ex);

            return ResponseEntity.status(500)
                    .body(new ApiResponse<>(false, "Ocorreu um erro inesperado no servidor. Tente novamente mais tarde.", null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteById(@PathVariable Integer id) {
        service.deleteById(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Fornecedor deletado com sucesso.", null));
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


    @GetMapping("/active")
    public ResponseEntity<ApiResponse<List<Provider>>> findAllActive() {
        List<Provider> activeProviders = service.findAllActive();
        return ResponseEntity.ok(new ApiResponse<>(true, "Prestadores ativos listados com sucesso.", activeProviders));
    }
}
