package com.orcafacil.api.application.service.company;

import com.orcafacil.api.domain.address.Address;
import com.orcafacil.api.domain.company.Company;
import com.orcafacil.api.domain.company.CompanyRepository;
import com.orcafacil.api.interfaceadapter.request.company.CreateCompanyRequest;
import com.orcafacil.api.interfaceadapter.request.company.UpdateCompanyRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final Validator validator;

    public CompanyService(CompanyRepository companyRepository, Validator validator) {
        this.companyRepository = companyRepository;
        this.validator = validator;
    }

    @Transactional
    public Company create(CreateCompanyRequest request) {
        validate(request);

        Address address = new Address(
                request.getAddress().getId(),
                request.getAddress().getZipCode(),
                request.getAddress().getStreet(),
                request.getAddress().getNumber(),
                request.getAddress().getNeighborhood(),
                request.getAddress().getCity(),
                request.getAddress().getState(),
                request.getAddress().getComplement()
        );

        Company company = new Company(
                null,
                request.getLegalName(),
                request.getCnpj(),
                address,
                new Date()
        );

        return companyRepository.save(company);
    }

    @Transactional
    public Company update(UpdateCompanyRequest request) {
        validate(request);

        Company existing = companyRepository.findByCnpj(request.getCnpj())
                .orElseGet(() -> companyRepository.findByAll()
                        .stream()
                        .filter(c -> request.getId() != null && request.getId().equals(c.getId()))
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("Empresa não encontrada"))
                );

        Address address = new Address(
                request.getAddress().getId(),
                request.getAddress().getZipCode(),
                request.getAddress().getStreet(),
                request.getAddress().getNumber(),
                request.getAddress().getNeighborhood(),
                request.getAddress().getCity(),
                request.getAddress().getState(),
                request.getAddress().getComplement()
        );

        Company updated = new Company(
                existing.getId(),
                request.getLegalName(),
                request.getCnpj(),
                address,
                existing.getCreatedAt()
        );

        return companyRepository.update(updated);
    }

    @Transactional(readOnly = true)
    public Optional<Company> getByUserId(Integer id) {
        if (id == null || id <= 0) throw new IllegalArgumentException("ID do usuário inválido.");
        return companyRepository.getByUserId(id);
    }

    @Transactional(readOnly = true)
    public boolean existsByCnpj(String cnpj) {
        if (cnpj == null || !cnpj.matches("\\d{14}")) {
            throw new IllegalArgumentException("CNPJ deve conter 14 dígitos numéricos.");
        }
        return companyRepository.findByCnpj(cnpj).isPresent();
    }

    @Transactional(readOnly = true)
    public List<Company> findAll() {
        return companyRepository.findByAll();
    }

    @Transactional
    public void deleteById(Integer id) {
        if (id == null || id <= 0) throw new IllegalArgumentException("ID da empresa inválido.");
        companyRepository.deleteByCompanyId(id);
    }

    private void validate(Object bean) {
        Set<ConstraintViolation<Object>> violations = validator.validate(bean);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }

    @Transactional(readOnly = true)
    public Optional<Company> findById(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido para busca.");
        }
        return companyRepository.findById(id);
    }
}
