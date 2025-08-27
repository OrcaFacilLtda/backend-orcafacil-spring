package com.orcafacil.api.application.service.provider;

import com.orcafacil.api.application.service.address.AddressService;
import com.orcafacil.api.application.service.category.CategoryService;
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
import com.orcafacil.api.interfaceadapter.request.user.UserUpdateRequest;
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

    public ProviderService(
            ProviderRepository providerRepository,
            UserRepository userRepository,
            CategoryService categoryService,
            PasswordEncoder passwordEncoder,
            ServiceRepository serviceRepository,
            AddressService addressService
    ) {
        this.providerRepository = providerRepository;
        this.userRepository = userRepository;
        this.categoryService = categoryService;
        this.passwordEncoder = passwordEncoder;
        this.serviceRepository = serviceRepository;
        this.addressService = addressService;
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

        User userToUpdate = existingProvider.getUser();
        Company companyToUpdate = existingProvider.getCompany();
        Category categoryToUpdate = existingProvider.getCategory();

        UserUpdateRequest userRequest = request.getUserUpdateRequest();
        if (userRequest != null) {
            if (userRequest.getName() != null) userToUpdate = userToUpdate.withName(userRequest.getName());
            if (userRequest.getPhone() != null) userToUpdate = userToUpdate.withPhone(userRequest.getPhone());
            if (userRequest.getEmail() != null) userToUpdate = userToUpdate.withEmail(userRequest.getEmail());

            if (userRequest.getAddress() != null) {
                Address updatedAddress = addressService.updateAddress(userRequest.getAddress());
                userToUpdate = userToUpdate.withAddress(updatedAddress);
                companyToUpdate = companyToUpdate.withAddress(updatedAddress);
            }

            if (userRequest.getPassword() != null && !userRequest.getPassword().isEmpty()) {
                if (userRequest.getCurrentPassword() == null || !passwordEncoder.matches(userRequest.getCurrentPassword(), userToUpdate.getPassword())) {
                    throw new RuntimeException("Senha atual incorreta.");
                }
                userToUpdate = userToUpdate.withPassword(passwordEncoder.encode(userRequest.getPassword()));
            }
        }

        if (request.getCompanyUpdateRequest() != null && request.getCompanyUpdateRequest().getLegalName() != null) {
            companyToUpdate = companyToUpdate.withLegalName(request.getCompanyUpdateRequest().getLegalName());
        }

        if (request.getCategoryId() != null && !request.getCategoryId().equals(categoryToUpdate.getId())) {
            categoryToUpdate = categoryService.findById(request.getCategoryId())
                    .orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada."));
        }

        Provider finalUpdatedProvider = new Provider(userToUpdate, companyToUpdate, categoryToUpdate);

        return providerRepository.save(finalUpdatedProvider);
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