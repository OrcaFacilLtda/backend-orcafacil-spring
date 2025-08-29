package com.orcafacil.api.application.service.provider;

import com.orcafacil.api.application.service.address.AddressService;
import com.orcafacil.api.application.service.category.CategoryService;
import com.orcafacil.api.application.service.company.CompanyService;
import com.orcafacil.api.application.service.user.UserService;
import com.orcafacil.api.domain.address.Address;
import com.orcafacil.api.domain.category.Category;
import com.orcafacil.api.domain.company.Company;
import com.orcafacil.api.domain.provider.Provider;
import com.orcafacil.api.domain.provider.ProviderRepository;
import com.orcafacil.api.domain.service.ServiceRepository;
import com.orcafacil.api.domain.service.ServiceStatus;
import com.orcafacil.api.domain.user.User;
import com.orcafacil.api.domain.user.UserRepository;
import com.orcafacil.api.domain.user.UserStatus;
import com.orcafacil.api.domain.user.UserType;
import com.orcafacil.api.interfaceadapter.request.provider.CreateProviderRequest;
import com.orcafacil.api.interfaceadapter.request.provider.UpdateProviderRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ProviderService {

    private final ProviderRepository providerRepository;
    private final UserRepository userRepository;
    private final CategoryService categoryService;
    private final PasswordEncoder passwordEncoder;
    private final ServiceRepository serviceRepository;
    private final AddressService addressService;
    private final UserService userService;
    private final CompanyService companyService;

    public ProviderService(
            ProviderRepository providerRepository,
            UserRepository userRepository,
            CategoryService categoryService,
            PasswordEncoder passwordEncoder,
            ServiceRepository serviceRepository,
            AddressService addressService,
            UserService userService,
            CompanyService companyService
    ) {
        this.providerRepository = providerRepository;
        this.userRepository = userRepository;
        this.categoryService = categoryService;
        this.passwordEncoder = passwordEncoder;
        this.serviceRepository = serviceRepository;
        this.addressService = addressService;
        this.userService = userService;
        this.companyService = companyService;
    }

    @Transactional
    public Provider create(CreateProviderRequest request) {
        if (userRepository.findByEmail(request.getUserRequest().getEmail()).isPresent()) {
            throw new IllegalArgumentException("E-mail já cadastrado.");
        }
        if (userRepository.findByCpf(request.getUserRequest().getCpf()).isPresent()) {
            throw new IllegalArgumentException("CPF já cadastrado.");
        }

        Category category = categoryService.findById(request.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada."));

        Address sharedAddress = new Address(
                null,
                request.getUserRequest().getAddress().getZipCode(),
                request.getUserRequest().getAddress().getStreet(),
                request.getUserRequest().getAddress().getNumber(),
                request.getUserRequest().getAddress().getNeighborhood(),
                request.getUserRequest().getAddress().getCity(),
                request.getUserRequest().getAddress().getState(),
                request.getUserRequest().getAddress().getComplement()
        );

        String hashedPassword = passwordEncoder.encode(request.getUserRequest().getPassword());

        User user = new User(
                null, request.getUserRequest().getName(), request.getUserRequest().getPhone(),
                request.getUserRequest().getEmail(), hashedPassword, request.getUserRequest().getCpf(),
                UserType.PROVIDER, request.getUserRequest().getBirthDate(), UserStatus.PENDING,
                sharedAddress
        );

        Company company = new Company(
                null, request.getCompanyRequest().getLegalName(), request.getCompanyRequest().getCnpj(),
                sharedAddress, new Date()
        );
        Provider provider = new Provider(user, company, category);
        return providerRepository.save(provider);
    }

    @Transactional(readOnly = true)
    public Optional<Provider> findById(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido para busca.");
        }
        return providerRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Provider> findByCompanyId(Integer companyId) {
        if (companyId == null || companyId <= 0) {
            throw new IllegalArgumentException("ID da empresa inválido para busca.");
        }
        return providerRepository.findByCompanyId(companyId);
    }

    @Transactional
    public void deleteById(Integer providerId) {
        Provider provider = providerRepository.findById(providerId)
                .orElseThrow(() -> new IllegalArgumentException("Fornecedor não encontrado"));

        boolean hasActiveServices = serviceRepository.findByUserId(provider.getUser().getId()).stream()
                .anyMatch(service ->
                        service.getServiceStatus() != ServiceStatus.COMPLETED &&
                                service.getServiceStatus() != ServiceStatus.REJECTED
                );

        if (hasActiveServices) {
            throw new IllegalStateException("Este fornecedor não pode ser excluído, pois possui serviços em andamento.");
        }

        providerRepository.deleteById(providerId);
    }


    @Transactional
    public Provider update(Integer providerId, UpdateProviderRequest request) {
        if (providerId == null || providerId <= 0) {
            throw new IllegalArgumentException("ID do provider inválido.");
        }

        Provider existingProvider = providerRepository.findById(providerId)
                .orElseThrow(() -> new IllegalArgumentException("Provider não encontrado"));

        if (request.getUserUpdateRequest() != null) {
            userService.update(existingProvider.getUser().getId(), request.getUserUpdateRequest());
        }

        // Update Company if request data is present
        if (request.getCompanyUpdateRequest() != null) {
            companyService.update(request.getCompanyUpdateRequest());
        }

        if (request.getCategoryId() != null && !request.getCategoryId().equals(existingProvider.getCategory().getId())) {
            Category newCategory = categoryService.findById(request.getCategoryId())
                    .orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada."));
            Provider updatedProvider = existingProvider.withCategory(newCategory);
            return providerRepository.save(updatedProvider);
        }

        return existingProvider;
    }


    @Transactional(readOnly = true)
    public List<Provider> findAllActive() {
        List<User> activeProviderUsers = userRepository
                .findByTypeAndStatus(UserType.PROVIDER, UserStatus.APPROVED);

        if (activeProviderUsers.isEmpty()) {
            return Collections.emptyList();
        }

        return providerRepository.findAllByUserIn(activeProviderUsers);
    }

    @Transactional(readOnly = true)
    public List<Provider> findAll() {
        return providerRepository.findAll();
    }
}