package com.orcafacil.api.application.service.provider;

import com.orcafacil.api.application.service.address.AddressService;
import com.orcafacil.api.application.service.company.CompanyService;
import com.orcafacil.api.application.service.user.UserService;
import com.orcafacil.api.application.service.category.CategoryService;
import com.orcafacil.api.domain.address.Address;
import com.orcafacil.api.domain.company.Company;
import com.orcafacil.api.domain.provider.Provider;
import com.orcafacil.api.domain.provider.ProviderRepository;
import com.orcafacil.api.domain.user.User;
import com.orcafacil.api.domain.category.Category;
import com.orcafacil.api.interfaceadapter.request.provider.CreateProviderRequest;
import com.orcafacil.api.interfaceadapter.request.provider.UpdateProviderRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ProviderService {
    private final ProviderRepository repository;
    private final CompanyService companyService;
    private final UserService userService;
    private final AddressService addressService;
    private final CategoryService categoryService;

    public ProviderService(
            ProviderRepository repository,
            CompanyService companyService,
            UserService userService,
            AddressService addressService,
            CategoryService categoryService
    ) {
        this.repository = repository;
        this.companyService = companyService;
        this.userService = userService;
        this.addressService = addressService;
        this.categoryService = categoryService;
    }

    @Transactional
    public Provider create(CreateProviderRequest request) {
        if (request.getCategoryId() == null) {
            throw new IllegalArgumentException("Categoria inválida.");
        }

        Category category = categoryService.findById(request.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada."));

        if (userService.existsByEmail(request.getUserRequest().getEmail())) {
            throw new IllegalArgumentException("E-mail já cadastrado.");
        }
        if (userService.existsByCpf(request.getUserRequest().getCpf())) {
            throw new IllegalArgumentException("CPF já cadastrado.");
        }
        if (companyService.existsByCnpj(request.getCompanyRequest().getCnpj())) {
            throw new IllegalArgumentException("CNPJ já cadastrado.");
        }

        Address address = addressService.createAddress(request.getCompanyRequest().getAddress());
        User user = userService.create(request.getUserRequest(), address);
        Company company = companyService.create(request.getCompanyRequest());

        Provider provider = new Provider(user, company, category);
        return repository.save(provider);
    }

    public Optional<Provider> findById(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido para busca.");
        }
        return repository.findById(id);
    }

    public Optional<Provider> findByCompanyId(Integer companyId) {
        if (companyId == null || companyId <= 0) {
            throw new IllegalArgumentException("ID da empresa inválido para busca.");
        }
        return repository.findByCompanyId(companyId);
    }

    @Transactional
    public void deleteById(Integer providerId) {
        Provider provider = repository.findById(providerId)
                .orElseThrow(() -> new IllegalArgumentException("Provider não encontrado"));

        repository.deleteById(providerId);

        userService.delete(provider.getUser().getId());
        companyService.delete(provider.getCompany().getId());
    }

    @Transactional
    public Provider updateCategory(Integer providerId, Integer categoryId) {
        Provider existingProvider = repository.findById(providerId)
                .orElseThrow(() -> new IllegalArgumentException("Provider não encontrado"));

        Category newCategory = categoryService.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada."));

        Provider updatedProvider = new Provider(
                existingProvider.getUser(),
                existingProvider.getCompany(),
                newCategory
        );

        return repository.save(updatedProvider);
    }

    @Transactional
    public Provider update(Integer providerId, UpdateProviderRequest request) {
        Provider provider = repository.findById(providerId)
                .orElseThrow(() -> new IllegalArgumentException("Provider não encontrado"));

        if (request.getUserUpdateRequest() != null) {
            userService.update(provider.getUser().getId(), request.getUserUpdateRequest());
        }

        if (request.getCompanyUpdateRequest() != null) {
            companyService.update(provider.getCompany().getId(), request.getCompanyUpdateRequest());
        }

        Category category = provider.getCategory();
        if (request.getCategoryId() != null && !request.getCategoryId().equals(category.getId())) {
            category = categoryService.findById(request.getCategoryId())
                    .orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada."));
        }

        User updatedUser = userService.findById(provider.getUser().getId())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
        Company updatedCompany = companyService.findByUserId(provider.getCompany().getId())
                .orElseThrow(() -> new IllegalArgumentException("Empresa não encontrada"));

        Provider updatedProvider = new Provider(updatedUser, updatedCompany, category);

        return repository.save(updatedProvider);
    }
}