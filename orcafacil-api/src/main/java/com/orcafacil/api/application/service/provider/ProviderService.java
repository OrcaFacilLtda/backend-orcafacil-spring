package com.orcafacil.api.application.service.provider;

import com.orcafacil.api.application.service.company.CompanyService;
import com.orcafacil.api.application.service.user.UserService;
import com.orcafacil.api.application.service.category.CategoryService;
import com.orcafacil.api.domain.company.Company;
import com.orcafacil.api.domain.provider.Provider;
import com.orcafacil.api.domain.provider.ProviderRepository;
import com.orcafacil.api.domain.user.User;
import com.orcafacil.api.domain.category.Category;
import com.orcafacil.api.domain.user.UserStatus;
import com.orcafacil.api.domain.user.UserType;
import com.orcafacil.api.interfaceadapter.request.provider.CreateProviderRequest;
import com.orcafacil.api.interfaceadapter.request.provider.UpdateProviderRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ProviderService {

    private final ProviderRepository repository;
    private final CompanyService companyService;
    private final UserService userService;
    private final CategoryService categoryService;

    public ProviderService(
            ProviderRepository repository,
            CompanyService companyService,
            UserService userService,
            CategoryService categoryService
    ) {
        this.repository = repository;
        this.companyService = companyService;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @Transactional
    public Provider create(CreateProviderRequest request) {
        if (request.getCategoryId() == null) {
            throw new IllegalArgumentException("Categoria é obrigatória.");
        }
        if (request.getUserRequest() == null) {
            throw new IllegalArgumentException("Usuário é obrigatório.");
        }
        if (request.getCompanyRequest() == null) {
            throw new IllegalArgumentException("Companhia é obrigatória.");
        }
        if (request.getCompanyRequest().getAddress() == null) {
            throw new IllegalArgumentException("Endereço da companhia é obrigatório.");
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

        User user = userService.create(request.getUserRequest());
        Company company = companyService.create(request.getCompanyRequest());

        Provider provider = new Provider(user, company, category);
        return repository.save(provider);
    }

    @Transactional(readOnly = true)
    public Optional<Provider> findById(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido para busca.");
        }
        return repository.findById(id);
    }

    @Transactional(readOnly = true)
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
        companyService.deleteById(provider.getCompany().getId());
    }

    @Transactional
    public Provider update(Integer providerId, UpdateProviderRequest request) {
        if (providerId == null || providerId <= 0) {
            throw new IllegalArgumentException("ID do provider inválido.");
        }

        Provider provider = repository.findById(providerId)
                .orElseThrow(() -> new IllegalArgumentException("Provider não encontrado"));

        if (request.getUserUpdateRequest() != null) {
            userService.update(provider.getUser().getId(), request.getUserUpdateRequest());
        }

        if (request.getCompanyUpdateRequest() != null) {
            companyService.update(request.getCompanyUpdateRequest());
        }


        Category updatedCategory = provider.getCategory();
        if (request.getCategoryId() != null && !request.getCategoryId().equals(updatedCategory.getId())) {
            updatedCategory = categoryService.findById(request.getCategoryId())
                    .orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada."));
        }

        User updatedUser = userService.findById(provider.getUser().getId())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));
        Company updatedCompany = companyService.findById(provider.getCompany().getId())
                .orElseThrow(() -> new IllegalArgumentException("Empresa não encontrada"));

        Provider updatedProvider = provider
                .withUser(updatedUser)
                .withCompany(updatedCompany)
                .withCategory(updatedCategory);

        return repository.save(updatedProvider);
    }

    @Transactional(readOnly = true)
    public List<Provider> findAllActive() {
        List<User> activeProviderUsers = userService
                .findByTypeAndStatus(UserType.PROVIDER, UserStatus.APPROVED);

        if (activeProviderUsers.isEmpty()) {
            return Collections.emptyList();
        }

        return repository.findAllByUserIn(activeProviderUsers);
    }

    @Transactional(readOnly = true)
    public List<Provider> findAll() {
        return repository.findAll();
    }
}
