package com.orcafacil.api.domain.provider;

import com.orcafacil.api.domain.category.Category;

import java.util.Optional;

public interface ProviderRepository {
    Provider save(Provider provider);
    Optional<Provider> findById(Integer id);
    Optional<Provider> findByCompanyId(Integer companyId);
    void deleteById(Integer id);
    Provider update(Provider provider);
    boolean existsById(Integer id);

}