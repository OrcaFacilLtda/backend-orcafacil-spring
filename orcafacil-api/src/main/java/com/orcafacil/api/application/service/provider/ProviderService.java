package com.orcafacil.api.application.service.provider;

import com.orcafacil.api.domain.category.Category;
import com.orcafacil.api.domain.company.Company;
import com.orcafacil.api.domain.provider.Provider;
import com.orcafacil.api.domain.provider.ProviderRepository;
import com.orcafacil.api.domain.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ProviderService {
    private final ProviderRepository repository;

    public ProviderService(ProviderRepository repository) {this.repository = repository;}

    @Transactional
    public Provider create(User userId, Company companyId, Category categoryId) {
        Provider provider = new Provider(userId, companyId,categoryId);return repository.save(provider);
    }

    public Optional<Provider> findById(Integer id) {
        return repository.findById(id);
    }

    public Optional<Provider> findByCompanyId(Integer companyId) {
        return repository.findByCompanyId(companyId);
    }

    @Transactional
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }
}