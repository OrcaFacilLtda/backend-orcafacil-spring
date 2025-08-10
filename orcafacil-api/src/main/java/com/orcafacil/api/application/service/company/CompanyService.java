package com.orcafacil.api.application.service.company;

import com.orcafacil.api.application.service.address.AddressService;
import com.orcafacil.api.domain.address.Address;
import com.orcafacil.api.domain.company.Company;
import com.orcafacil.api.domain.company.CompanyRepository;
import com.orcafacil.api.interfaceadapter.request.company.UpdateCompanyRequest;
import com.orcafacil.api.interfaceadapter.request.company.CreateCompanyRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final AddressService addressService;
    private final Validator validator;

    public CompanyService(CompanyRepository companyRepository, AddressService addressService, Validator validator) {
        this.companyRepository = companyRepository;
        this.addressService = addressService;
        this.validator = validator;
    }

    public Company create(CreateCompanyRequest request) {
        Set<ConstraintViolation<CreateCompanyRequest>> violations = validator.validate(request);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException("Dados da empresa inválidos", violations);
        }

        Address savedAddress = addressService.createAddress(request.getAddress());

        Company company = new Company(
                null,
                request.getLegalName(),
                request.getCnpj(),
                savedAddress,
                null
        );

        return companyRepository.save(company);
    }

    public Company update(Integer id, UpdateCompanyRequest request) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID da empresa inválido.");
        }

        Set<ConstraintViolation<UpdateCompanyRequest>> violations = validator.validate(request);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException("Dados de atualização inválidos", violations);
        }

        Company existingCompany = companyRepository.getByUserId(id)
                .orElseThrow(() -> new RuntimeException("Empresa com o ID " + id + " não encontrada"));

        Address updatedAddress = existingCompany.getAddress();
        if (request.getAddress() != null && request.getAddress().getId() != null) {
            updatedAddress = addressService.updateAddress(request.getAddress());
        }

        String legalName;
        if (request.getLegalName() != null) {
            legalName = request.getLegalName();
        } else {
            legalName = existingCompany.getLegalName();
        }

        String cnpj;
        if (request.getCnpj() != null) {
            cnpj = request.getCnpj();
        } else {
            cnpj = existingCompany.getCnpj();
        }

        Company updatedCompany = existingCompany
                .withLegalName(legalName)
                .withCnpj(cnpj)
                .withAddress(updatedAddress);

        return companyRepository.update(updatedCompany);
    }


    public void delete(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID da empresa inválido.");
        }

        Optional<Company> companyOpt = companyRepository.getByUserId(id);
        companyOpt.ifPresent(company -> {
            companyRepository.deleteByCompanyId(id);
        });
    }

    public Optional<Company> findByUserId(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido.");
        }

        return companyRepository.getByUserId(id);
    }

    public boolean existsByCnpj(String cnpj) {
        if (cnpj == null || !cnpj.matches("\\d{14}")) {
            throw new IllegalArgumentException("CPF deve conter 11 dígitos numéricos.");
        }
        return companyRepository.findByCnpj(cnpj).isPresent();
    }
}